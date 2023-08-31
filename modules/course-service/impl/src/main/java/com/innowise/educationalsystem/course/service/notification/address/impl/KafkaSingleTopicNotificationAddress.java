package com.innowise.educationalsystem.course.service.notification.address.impl;

import com.innowise.educationalsystem.course.service.notification.address.NotificationAddress;
import com.innowise.educationalsystem.course.service.notification.address.NotificationAddressDetails;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class KafkaSingleTopicNotificationAddress implements NotificationAddress { // TODO: Template for Kafka etc ???
    private final String topic;

    @Override
    public NotificationAddressDetails<String> addressDetails() {
        return () -> topic;
    }
}
