package com.mt.kafka.demo.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@ConditionalOnProperty(prefix = "kafka.project.demo", name = "flag", havingValue = "true", matchIfMissing = false)
@Configuration
public class KafkaConfigs {

    @Value("${topic.name}")
    private String topicName;

    @Value("${topic.name.no.group}")
    private String topicNameNoGroup;

    /**
     * Here we create a Kafka topic without group
     *
     * @return
     */
    @ConditionalOnProperty(prefix = "topic.no.group", name = "flag", havingValue = "true", matchIfMissing = true)
    @Bean(name = "topicNoGroup")
    public NewTopic topicNoGroup() {
        return TopicBuilder.name(topicNameNoGroup)
                .partitions(3)
                .replicas(1)
                .build();
    }

    /**
     * Here we create a kafka topic with a group assigned to it
     *
     * @return
     */
    @ConditionalOnProperty(prefix = "topic.no.group", name = "flag", havingValue = "false")
    @Bean(name = "topicWithGroup")
    public NewTopic topicWithGroups() {
        return TopicBuilder.name(topicName)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
