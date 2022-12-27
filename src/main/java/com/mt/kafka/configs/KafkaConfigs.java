package com.mt.kafka.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaConfigs {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConfigs.class);

    @Value("${topic.name}")
    private String topicName;

    @Value("${topic.name.no.group}")
    private String topicNameNoGroup;

    @ConditionalOnProperty(prefix = "topic.no.group", name = "flag", havingValue = "true", matchIfMissing = true)
    @Bean(name = "topicNoGroup")
    public NewTopic topicNoGroup() {
        return TopicBuilder.name(topicNameNoGroup)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @ConditionalOnProperty(prefix = "topic.no.group", name = "flag", havingValue = "false")
    @Bean(name = "topicWithGroup")
    public NewTopic topicWithGroups() {
        return TopicBuilder.name(topicName)
                .partitions(3)
                .replicas(1)
                .build();
    }

    /*
    @KafkaListener(id = "kafka-demo-listener", topics = "kafka-demo-three-partitions-springboot")
    public void listen(String inComeMessage) {
        logger.info("Kafka listener : {}", inComeMessage);
    }*/

    /*
    @Bean
    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
        return args -> {
            template.send("kafka-demo-three-partitions-springboot", "spring-boot-test-data");
        };
    }*/
}
