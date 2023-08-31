package com.innowise.educationalsystem.course.service;

import com.innowise.educationalsystem.course.service.notification.Notification;

public interface NotificationService {
    void sendNotification(Notification notification);

}
