package com.innowise.educationalsystem.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties("app.security")
public class SecurityProperties {
    private String secretKey;

    private Duration tokenExpire;
}
