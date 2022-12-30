package com.mt.kafka.demo.controllers;


import com.mt.kafka.demo.producers.IKafkaKeyProducer;
import com.mt.kafka.demo.producers.IKafkaProducer;
import com.mt.kafka.demo.producers.KafkaProducer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

@ConditionalOnProperty(prefix = "kafka.project.demo", name = "flag", havingValue = "true", matchIfMissing = false)
@RestController
@RequestMapping("/kafka")
public class KafkaControllers {

    private final IKafkaProducer<String> kafkaProducer;
    private final IKafkaKeyProducer<String> kafkaKeyProducer;

    public KafkaControllers(KafkaProducer<String> kafkaProducer, IKafkaKeyProducer<String> kafkaKeyProducer) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaKeyProducer = kafkaKeyProducer;
    }

    @PostMapping(value = "/produce")
    public void sendMessageToKafka(@RequestParam("message") String message) {
        this.kafkaProducer.sendMessage(message);
    }

    @PostMapping(value = "/produceCallback")
    public void sendMessageToKafkaCallback(@RequestParam("message") String message) {
        this.kafkaProducer.sendWithCallback(message);
    }

    @PostMapping(value = "/produceWithKeys")
    public void sendMessageToKafkaWithKeys(@RequestParam("message") String message) {
        Random random = new Random();
        int randomNumber = random.nextInt(0, 10);
        this.kafkaKeyProducer.sendWithKey(message, "myKey" + randomNumber);
    }
}
