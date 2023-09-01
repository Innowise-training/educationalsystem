package com.innowise.educationalsystem.dto.mail;

import lombok.Data;

import java.util.Map;
import java.util.Optional;

@Data
public class AttemptRequest {
    private String mailId;

    private Optional<Map<String, Object>> payload;
}
