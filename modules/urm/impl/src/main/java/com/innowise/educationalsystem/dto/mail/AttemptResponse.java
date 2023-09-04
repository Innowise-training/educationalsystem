package com.innowise.educationalsystem.dto.mail;

import lombok.Data;

@Data
public class AttemptResponse {
    private MailStatus attemptStatus;

    private String mailId;

    private String attemptId;

    private int version;
}
