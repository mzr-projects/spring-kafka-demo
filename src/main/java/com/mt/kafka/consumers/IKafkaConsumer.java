package com.mt.kafka.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface IKafkaConsumer {

    void consume(ConsumerRecord<Integer, String> consumerRecord);
}
