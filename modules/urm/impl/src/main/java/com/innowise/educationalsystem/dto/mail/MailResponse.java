package com.innowise.educationalsystem.dto.mail;

import lombok.Data;

@Data
public class MailResponse {
    private String mailId;

    private MailStatus creationStatus;
}
