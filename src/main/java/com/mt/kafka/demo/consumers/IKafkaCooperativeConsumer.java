package com.mt.kafka.demo.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface IKafkaCooperativeConsumer {

    void firstCooperativeConsumer(ConsumerRecord<Integer, String> consumerRecord);

    void secondCooperativeConsumer(ConsumerRecord<Integer, String> consumerRecord);
}
