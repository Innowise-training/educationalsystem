package com.innowise.educationalsystem.course.service.notification.payload.impl;

import com.innowise.educationalsystem.course.dto.AttemptRequest;
import com.innowise.educationalsystem.course.service.notification.content.NotificationContent;
import com.innowise.educationalsystem.course.service.notification.payload.NotificationPayload;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MailAttemptRequestPayload implements NotificationPayload {
    private final AttemptRequest attemptRequest;

    @Override
    public NotificationContent<AttemptRequest> notificationContent() {
        return () -> attemptRequest;
    }
}
