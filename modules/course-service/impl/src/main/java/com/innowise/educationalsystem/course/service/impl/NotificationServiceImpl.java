package com.innowise.educationalsystem.course.service.impl;

import com.innowise.educationalsystem.course.service.NotificationService;
import com.innowise.educationalsystem.course.service.notification.Notification;
import com.innowise.educationalsystem.course.service.notification.NotificationSenderProvider;
import com.innowise.educationalsystem.course.service.notification.address.NotificationAddress;
import com.innowise.educationalsystem.course.service.notification.payload.NotificationPayload;
import com.innowise.educationalsystem.course.service.notification.sender.NotificationSender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationSenderProvider notificationSenderProvider;

    @Override
    public void sendNotification(Notification notification) {
        NotificationSender<NotificationPayload, NotificationAddress> notificationSender = notificationSenderProvider
                .getSenderByType(notification.payload().getClass(), notification.address().getClass());
        notificationSender.send(notification.payload(), notification.address());
    }
}
