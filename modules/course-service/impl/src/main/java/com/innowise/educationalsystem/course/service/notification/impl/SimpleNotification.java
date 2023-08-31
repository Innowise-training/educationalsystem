package com.innowise.educationalsystem.course.service.notification.impl;

import com.innowise.educationalsystem.course.service.notification.Notification;
import com.innowise.educationalsystem.course.service.notification.address.NotificationAddress;
import com.innowise.educationalsystem.course.service.notification.payload.NotificationPayload;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleNotification implements Notification {
    private final String id;

    private final NotificationPayload notificationPayload;

    private final NotificationAddress notificationAddress;

    @Override
    public String id() {
        return id;
    }

    @Override
    public NotificationPayload payload() {
        return notificationPayload;
    }

    @Override
    public NotificationAddress address() {
        return notificationAddress;
    }
}
