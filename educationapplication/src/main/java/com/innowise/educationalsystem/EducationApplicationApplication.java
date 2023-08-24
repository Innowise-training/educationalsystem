package com.innowise.educationalsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.innowise.educationalsystem")
public class EducationApplicationApplication {
    public static void main(String[] args) {
        SpringApplication.run(EducationApplicationApplication.class, args);
    }
}
