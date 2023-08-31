package com.innowise.educationalsystem.course.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "app.kafka")
public class KafkaConfigProperties {
    private Map<String, String> topics;

    private Map<String, String> groups;
}
