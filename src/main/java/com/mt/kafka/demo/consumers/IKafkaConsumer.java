package com.mt.kafka.demo.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;

public interface IKafkaConsumer {

    void firstConsumer(ConsumerRecord<Integer, String> consumerRecord);

    void secondConsumer(ConsumerRecord<Integer, String> consumerRecord);

}
