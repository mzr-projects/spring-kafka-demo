package com.mt.kafka.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer implements IKafkaConsumer {

    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "kafka-demo-three-partitions-springboot-no-group", groupId = "spring-boot-kafka-demo-group")
    public void consume(ConsumerRecord<Integer, String> consumerRecord) {
        logger.info("CONSUMER -> message : {}, topic : {}, key : {}, partition : {}, offset : {}",
                consumerRecord.value(),
                consumerRecord.topic(),
                consumerRecord.key(),
                consumerRecord.partition(),
                consumerRecord.offset());
    }

    /*
    @KafkaListener(topics = "kafka-demo-three-partitions-springboot")
    public void consume(String message) {
        logger.info("Consumed message : {}", message);
    }
    */
}
