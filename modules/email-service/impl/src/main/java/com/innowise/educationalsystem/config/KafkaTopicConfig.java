package com.innowise.educationalsystem.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@PropertySource(value = "classpath:kafka-props.yaml", factory = YamlPropertySourceFactory.class)
public class KafkaTopicConfig {
    @Bean
    public NewTopic mailRequestTopic(@Value("${kafka.topic.mail-request}") String mailRequestTopicName) {
        return TopicBuilder
            .name(mailRequestTopicName)
            .build();
    }

    @Bean
    public NewTopic mailResponseTopic(@Value("${kafka.topic.mail-response}") String mailResponseTopicName) {
        return TopicBuilder
            .name(mailResponseTopicName)
            .build();
    }

    @Bean
    public NewTopic attemptRequestTopic(@Value("${kafka.topic.attempt-request}") String attemptRequestTopicName) {
        return TopicBuilder
            .name(attemptRequestTopicName)
            .build();
    }

    @Bean
    public NewTopic attemptResponseTopic(@Value("${kafka.topic.attempt-response}") String attemptResponseTopicName) {
        return TopicBuilder
            .name(attemptResponseTopicName)
            .build();
    }
}
