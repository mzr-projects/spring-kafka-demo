package com.mt.kafka.demo.producers;

import org.springframework.web.bind.annotation.RequestParam;

public interface IKafkaProducer<T> {
    void sendMessage(T message);

    void sendWithCallback(T message);
}
