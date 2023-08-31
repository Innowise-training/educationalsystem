package com.innowise.educationalsystem.course.service.notification.payload;

import com.innowise.educationalsystem.course.service.notification.content.NotificationContent;

public interface NotificationPayload {

    NotificationContent<?> notificationContent();

}
