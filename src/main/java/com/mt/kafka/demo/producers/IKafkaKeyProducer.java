package com.mt.kafka.demo.producers;

public interface IKafkaKeyProducer<T> {
    void sendWithKey(T message, T key);
}
