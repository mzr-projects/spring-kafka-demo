package com.mt.kafka.producers;

import org.springframework.web.bind.annotation.RequestParam;

public interface IKafkaProducer<T> {
    void sendMessage(T message);

    void sendWithCallback(T message);

    void sendWithKey(T message, T key);
}
