package com.mt.kafka.producers;

import org.springframework.web.bind.annotation.RequestParam;

public interface IKafkaProducer {
    void sendMessage(String message);
}
