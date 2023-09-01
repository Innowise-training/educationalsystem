package com.innowise.educationalsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication(scanBasePackages = "com.innowise.educationalsystem")
public class EducationApplicationApplication {
    public static void main(String[] args) {
        SpringApplication.run(EducationApplicationApplication.class, args);
    }
}
