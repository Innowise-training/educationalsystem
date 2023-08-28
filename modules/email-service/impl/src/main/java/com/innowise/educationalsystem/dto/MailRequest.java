package com.innowise.educationalsystem.dto;

import com.innowise.educationalsystem.entity.enums.MailType;
import java.util.Map;
import lombok.Data;

@Data
public class MailRequest {
    private MailType mailType;

    private String destinationEmail;

    private Map<String, Object> payload;
}
