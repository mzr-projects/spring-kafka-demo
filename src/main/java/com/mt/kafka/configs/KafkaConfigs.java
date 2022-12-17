package com.mt.kafka.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
public class KafkaConfigs {

    @Bean(name = "topic")
    public NewTopic topic() {
        return TopicBuilder.name("kafka-demo-three-partitions-springboot")
                .partitions(3)
                .replicas(1)
                .build();
    }

    @KafkaListener(id = "kafka-demo-listener", topics = "kafka-demo-three-partitions-springboot")
    public void listen(String in) {
        System.out.println(in);
    }

    @Bean
    public ApplicationRunner runner(KafkaTemplate<String, String> template) {
        return args -> {
            template.send("kafka-demo-three-partitions-springboot", "spring-boot-test-data");
        };
    }
}
