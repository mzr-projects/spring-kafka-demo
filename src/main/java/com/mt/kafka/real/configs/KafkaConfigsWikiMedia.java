package com.mt.kafka.real.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@ConditionalOnProperty(prefix = "kafka.project.demo", name = "flag", havingValue = "false")
@Configuration
public class KafkaConfigsWikiMedia {

    @Value("${topic.wiki.name}")
    private String wikiMediaTopic;

    /**
     * Here we create a kafka topic with a group assigned to it
     *
     * @return
     */
    @ConditionalOnProperty(prefix = "topic.no.group", name = "flag", havingValue = "false")
    @Bean(name = "wikiMediaTopic")
    public NewTopic topicWithGroups() {
        return TopicBuilder.name(wikiMediaTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
