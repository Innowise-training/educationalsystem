package com.innowise.educationalsystem.service;

import com.innowise.educationalsystem.entity.Version;

public interface SenderService {
    void sendMail(String destEmail, Version version);
}
