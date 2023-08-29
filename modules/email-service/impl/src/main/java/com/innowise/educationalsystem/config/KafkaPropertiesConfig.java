package com.innowise.educationalsystem.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;


@Getter
@Setter
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaPropertiesConfig {
    private String bootstrapServers;
}
