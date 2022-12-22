package com.mt.kafka.controllers;


import com.mt.kafka.producers.IKafkaProducer;
import com.mt.kafka.producers.KafkaProducer;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaControllers {

    private final IKafkaProducer<String> kafkaProducer;

    public KafkaControllers(KafkaProducer<String> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping(value = "/produce")
    public void sendMessageToKafka(@RequestParam("message") String message) {
        this.kafkaProducer.sendMessage(message);
    }

    @PostMapping(value = "/produceCallback")
    public void sendMessageToKafkaCallback(@RequestParam("message") String message) {
        this.kafkaProducer.sendWithCallback(message);
    }

}
