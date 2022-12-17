package com.mt.kafka.controllers;


import com.mt.kafka.producers.KafkaProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaControllers {

    private final KafkaProducer kafkaProducer;

    public KafkaControllers(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/produce")
    public void sendMessageToKafka(@RequestParam("message") String message) {
        this.kafkaProducer.sendMessage(message);
    }
}
