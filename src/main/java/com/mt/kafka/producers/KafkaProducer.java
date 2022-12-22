package com.mt.kafka.producers;

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
    private static final String TOPIC_NAME = "kafka-demo-three-partitions-springboot";

    private final KafkaTemplate<T, T> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<T, T> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(T message) {
        logger.info("Produced : {} in Topic : {}", message, TOPIC_NAME);
        kafkaTemplate.send(TOPIC_NAME, message);
    }

    public void sendWithCallback(T message) {
        CompletableFuture<SendResult<T, T>> future = kafkaTemplate.send(TOPIC_NAME, message);
        future.whenComplete(((stringStringSendResult, throwable) -> {
            RecordMetadata metaData = stringStringSendResult.getRecordMetadata();
            logger.info("Message in topic : {}, offset : {}, partition : {}",
                    metaData.topic(), metaData.offset(), metaData.partition());
        }));
    }
}
