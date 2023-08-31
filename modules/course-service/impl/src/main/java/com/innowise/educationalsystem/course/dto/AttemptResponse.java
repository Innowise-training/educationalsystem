package com.innowise.educationalsystem.course.dto;

import com.innowise.educationalsystem.course.dto.enums.AttemptStatus;
import lombok.Builder;
import lombok.Data;

@Data
public class AttemptResponse {
    private AttemptStatus attemptStatus;

    private String mailId;

    private String attemptId;

    private int version;
}
