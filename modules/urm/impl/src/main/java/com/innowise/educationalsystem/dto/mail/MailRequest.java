package com.innowise.educationalsystem.dto.mail;

import lombok.Data;

import java.util.Map;

@Data
public class MailRequest {
    private String mailType;

    private String destinationEmail;

    private Map<String, Object> payload;
}
