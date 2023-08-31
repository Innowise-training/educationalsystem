package com.innowise.educationalsystem.course.service.notification;

import com.innowise.educationalsystem.course.service.notification.address.NotificationAddress;
import com.innowise.educationalsystem.course.service.notification.payload.NotificationPayload;

public interface Notification {
    String id();

    NotificationPayload payload();

    NotificationAddress address();
}
