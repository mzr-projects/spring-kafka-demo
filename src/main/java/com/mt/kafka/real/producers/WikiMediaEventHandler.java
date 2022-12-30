package com.mt.kafka.real.producers;

import com.launchdarkly.eventsource.MessageEvent;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;

public class WikiMediaEventHandler<T> implements BackgroundEventHandler {

    private final Logger log = LoggerFactory.getLogger(WikiMediaEventHandler.class.getSimpleName());

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final String topic;

    public WikiMediaEventHandler(KafkaTemplate<String, String> kafkaTemplate, String topic) {
        this.kafkaTemplate = kafkaTemplate;
        this.topic = topic;
    }

    @Override
    public void onOpen() throws Exception {

    }

    @Override
    public void onClosed() throws Exception {

    }

    @Override
    public void onMessage(String s, MessageEvent messageEvent) throws Exception {
        CompletableFuture<SendResult<String, String>> callBack = kafkaTemplate.send(topic, messageEvent.getData());
        callBack.whenComplete((ttSendResult, throwable) -> {
            RecordMetadata metaData = ttSendResult.getRecordMetadata();
            log.info("WikiMediaProducer -> Message in topic : {}, offset : {}, partition : {}",
                    metaData.topic(), metaData.offset(), metaData.partition());
        });
    }

    @Override
    public void onComment(String s) throws Exception {

    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Error in stream : {}", throwable.getMessage());
    }
}
