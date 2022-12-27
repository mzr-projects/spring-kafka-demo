package com.mt.kafka.producers;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducer<T> implements IKafkaProducer<T> {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);

    private final NewTopic topicNoGroup;

    private final KafkaTemplate<T, T> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<T, T> kafkaTemplate, NewTopic topicNoGroup) {
        this.kafkaTemplate = kafkaTemplate;
        this.topicNoGroup = topicNoGroup;
    }

    public void sendMessage(T message) {
        logger.info("Produced : {} in Topic : {}", message, topicNoGroup.name());
        kafkaTemplate.send(topicNoGroup.name(), message);
    }

    public void sendWithCallback(T message) {
        CompletableFuture<SendResult<T, T>> future = kafkaTemplate.send(topicNoGroup.name(), message);
        future.whenComplete(((stringStringSendResult, throwable) -> {
            RecordMetadata metaData = stringStringSendResult.getRecordMetadata();
            logger.info("PRODUCER -> Message in topic : {}, offset : {}, partition : {}",
                    metaData.topic(), metaData.offset(), metaData.partition());
        }));
    }

    @Override
    public void sendWithKey(T message, T key) {
        CompletableFuture<SendResult<T, T>> future = kafkaTemplate.send(topicNoGroup.name(), key, message);
        future.whenComplete(((stringStringSendResult, throwable) -> {
            RecordMetadata metaData = stringStringSendResult.getRecordMetadata();
            logger.info("PRODUCER -> Message in topic : {}, offset : {}, partition : {}, key : {}",
                    metaData.topic(),
                    metaData.offset(),
                    metaData.partition(),
                    stringStringSendResult.getProducerRecord().key());
        }));
    }
}
