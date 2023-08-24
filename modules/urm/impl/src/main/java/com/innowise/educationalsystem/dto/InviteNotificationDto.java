package com.innowise.educationalsystem.dto;

import lombok.Data;

@Data
public class InviteNotificationDto {
    private String id;

    private String email;

    private long subscriptionId;
}
