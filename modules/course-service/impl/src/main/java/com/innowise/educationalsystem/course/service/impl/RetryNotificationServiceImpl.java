package com.innowise.educationalsystem.course.service.impl;

import com.innowise.educationalsystem.course.service.NotificationService;
import com.innowise.educationalsystem.course.service.RetryableService;
import com.innowise.educationalsystem.course.service.notification.Notification;
import com.innowise.educationalsystem.course.service.notification.NotificationSenderProvider;
import com.innowise.educationalsystem.course.service.notification.address.NotificationAddress;
import com.innowise.educationalsystem.course.service.notification.payload.NotificationPayload;
import com.innowise.educationalsystem.course.service.notification.sender.NotificationSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service("retryNotificationService")
@RequiredArgsConstructor
public class RetryNotificationServiceImpl implements NotificationService, RetryableService {
    private final NotificationSenderProvider notificationSenderProvider;

    private final Map<String, Notification> retryableNotificationMap = new HashMap<>();

    private final Map<String, Integer> retryableNotificationAttemptsMap = new HashMap<>();

    private static final String RETRY_ATTEMPTS_PROPERTY = "${app.retry.attempt}";

    @Value(RETRY_ATTEMPTS_PROPERTY)
    private int retryAttempts;

    private void init(Notification retryableNotification) {
        retryableNotificationMap.put(retryableNotification.id(), retryableNotification);
        retryableNotificationAttemptsMap.put(retryableNotification.id(), retryAttempts);
    }

    @Override
    public void retry(String id) {
        if (!isRetryableNotification(id)) {
            return;
        }

        log.info("Retrying notification {}...", id);
        Notification notification = retryableNotificationMap.get(id);
        int currentAttempt = retryableNotificationAttemptsMap.get(id);

        if (currentAttempt < 1) {
            log.error("Notification {} can't be retried again. Insufficient retry attempts.", notification);
            retryableNotificationMap.remove(id);
            retryableNotificationAttemptsMap.remove(id);
            return;
        }

        NotificationSender<NotificationPayload, NotificationAddress> notificationSender = notificationSenderProvider
                .getSenderByType(notification.payload().getClass(), notification.address().getClass());
        notificationSender.send(notification.payload(), notification.address());

        retryableNotificationAttemptsMap.replace(id, --currentAttempt);
        log.info("Notification {} has been resent", id);
    }

    @Override
    public boolean isRetryableNotification(String retryableNotification) {
        return retryableNotificationMap.containsKey(retryableNotification);
    }

    @Override
    public void sendNotification(Notification notification) {
        init(notification);
        NotificationSender<NotificationPayload, NotificationAddress> notificationSender = notificationSenderProvider
                .getSenderByType(notification.payload().getClass(), notification.address().getClass());
        notificationSender.send(notification.payload(), notification.address());
    }
}
