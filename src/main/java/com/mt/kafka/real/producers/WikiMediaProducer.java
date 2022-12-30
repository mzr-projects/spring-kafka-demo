package com.mt.kafka.real.producers;

import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.StreamException;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.concurrent.CompletableFuture;

@Service
public class WikiMediaProducer<T> {

    private final Logger log = LoggerFactory.getLogger(WikiMediaProducer.class.getSimpleName());

    private final KafkaTemplate<String, String> kafkaTemplate;

    private EventSource eventSource;

    @Value("${topic.wiki.name}")
    private String topicName;

    private static final String STREAM_URI = "https://stream.wikimedia.org/v2/stream/recentchange";

    public WikiMediaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void startStream() throws StreamException {
        EventSource.Builder builder = new EventSource.Builder(URI.create(STREAM_URI));
        eventSource = builder.build();
        eventSource.start();
        onReadMessage();
    }

    public void onReadMessage() throws StreamException {

        Iterable<MessageEvent> messageEvent = eventSource.messages();

        messageEvent.forEach(msg -> {
            log.info("Data from stream to Kafka : {}", msg.getData());
            CompletableFuture<SendResult<String, String>> callBack = kafkaTemplate.send(topicName, msg.getData());
            callBack.whenComplete((ttSendResult, throwable) -> {
                RecordMetadata metaData = ttSendResult.getRecordMetadata();
                log.info("WikiMediaProducer -> Message in topic : {}, offset : {}, partition : {}",
                        metaData.topic(), metaData.offset(), metaData.partition());
            });
        });

    }
}
