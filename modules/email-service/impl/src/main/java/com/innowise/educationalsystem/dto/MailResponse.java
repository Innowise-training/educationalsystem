package com.innowise.educationalsystem.dto;

import com.innowise.educationalsystem.entity.enums.CreationStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailResponse {
    private String mailId;

    private CreationStatus creationStatus;
}
