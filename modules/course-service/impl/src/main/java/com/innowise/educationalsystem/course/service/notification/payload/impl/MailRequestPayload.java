package com.innowise.educationalsystem.course.service.notification.payload.impl;

import com.innowise.educationalsystem.course.dto.MailRequest;
import com.innowise.educationalsystem.course.service.notification.content.NotificationContent;
import com.innowise.educationalsystem.course.service.notification.payload.NotificationPayload;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MailRequestPayload implements NotificationPayload {
    private final MailRequest mailRequest;

    @Override
    public NotificationContent<MailRequest> notificationContent() {
        return () -> mailRequest;
    }
}
