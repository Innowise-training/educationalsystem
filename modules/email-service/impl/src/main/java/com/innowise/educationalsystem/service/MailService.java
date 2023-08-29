package com.innowise.educationalsystem.service;

import com.innowise.educationalsystem.dto.MailRequest;
import com.innowise.educationalsystem.dto.MailResponse;
import com.innowise.educationalsystem.entity.Mail;

public interface MailService {
    MailResponse createMail(MailRequest mailRequest);
    Mail find(String mailId);
}
