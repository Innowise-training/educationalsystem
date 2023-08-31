package com.innowise.educationalsystem.course.service.notification.sender;

public interface NotificationSender<P, A> {
    void send(P payload, A address);
}
