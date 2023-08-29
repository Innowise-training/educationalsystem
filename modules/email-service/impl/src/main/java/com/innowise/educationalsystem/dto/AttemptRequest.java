package com.innowise.educationalsystem.dto;

import java.util.Map;
import java.util.Optional;
import lombok.Data;

@Data
public class AttemptRequest {
    private String mailId;

    private Optional<Map<String, Object>> payload;
}
