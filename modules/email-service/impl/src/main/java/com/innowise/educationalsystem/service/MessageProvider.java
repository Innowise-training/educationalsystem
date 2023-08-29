package com.innowise.educationalsystem.service;

import com.innowise.educationalsystem.entity.Version;
import javax.mail.internet.MimeMessage;

public interface MessageProvider {
    MimeMessage createMessage(Version version);
}
