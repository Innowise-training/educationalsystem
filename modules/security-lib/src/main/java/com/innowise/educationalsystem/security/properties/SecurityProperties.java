package com.innowise.educationalsystem.security.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties("app.security")
public class SecurityProperties {
    private String secretKey;

    private Duration tokenExpire;
}
