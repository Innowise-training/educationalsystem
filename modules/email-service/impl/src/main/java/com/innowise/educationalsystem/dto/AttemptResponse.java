package com.innowise.educationalsystem.dto;

import com.innowise.educationalsystem.entity.enums.AttemptStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AttemptResponse {
    private AttemptStatus attemptStatus;

    private String mailId;

    private String attemptId;

    private int version;
}
