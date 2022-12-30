package com.mt.kafka.demo.consumers;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(prefix = "kafka.project.demo", name = "flag", havingValue = "true", matchIfMissing = true)
@Service
public class KafkaConsumer implements IKafkaConsumer, IKafkaCooperativeConsumer {

    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "kafka-demo-three-partitions-springboot",
            groupId = "spring-boot-kafka-demo-group",
            id = "fC")
    public void firstConsumer(ConsumerRecord<Integer, String> consumerRecord) {
        logConsumersData("fC", consumerRecord);
    }

    @KafkaListener(topics = "kafka-demo-three-partitions-springboot",
            groupId = "spring-boot-kafka-demo-group",
            id = "sC")
    public void secondConsumer(ConsumerRecord<Integer, String> consumerRecord) {
        logConsumersData("sC", consumerRecord);
    }

    @KafkaListener(topics = "kafka-demo-three-partitions-springboot",
            groupId = "spring-boot-kafka-demo-group",
            id = "fCC",
            properties = {"partition.assignment.strategy=org.apache.kafka.clients.consumer.CooperativeStickyAssignor"})
    public void firstCooperativeConsumer(ConsumerRecord<Integer, String> consumerRecord) {
        logConsumersData("fCC", consumerRecord);
    }

    @KafkaListener(topics = "kafka-demo-three-partitions-springboot",
            groupId = "spring-boot-kafka-demo-group",
            id = "sCC",
            properties = {"partition.assignment.strategy=org.apache.kafka.clients.consumer.CooperativeStickyAssignor",
                    "enable.auto.commit=true"})
    public void secondCooperativeConsumer(ConsumerRecord<Integer, String> consumerRecord) {
        logConsumersData("sCC", consumerRecord);
    }

    private void logConsumersData(String id, ConsumerRecord<Integer, String> consumerRecord) {
        logger.info(id + " - CONSUMER -> message : {}, topic : {}, key : {}, partition : {}, offset : {}",
                consumerRecord.value(),
                consumerRecord.topic(),
                consumerRecord.key(),
                consumerRecord.partition(),
                consumerRecord.offset()
        );
    }
}
