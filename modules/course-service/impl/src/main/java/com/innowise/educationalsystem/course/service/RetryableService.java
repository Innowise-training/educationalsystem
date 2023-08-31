package com.innowise.educationalsystem.course.service;

public interface RetryableService {

    void retry(String id);

    boolean isRetryableNotification(String retryableNotification);
}
