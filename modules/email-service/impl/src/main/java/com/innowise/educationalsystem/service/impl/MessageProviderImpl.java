package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.entity.Version;
import com.innowise.educationalsystem.service.MessageProvider;
import javax.mail.internet.MimeMessage;
import org.springframework.stereotype.Service;

@Service
public class MessageProviderImpl implements MessageProvider {
    @Override
    public MimeMessage createMessage(Version version) {

        return null;
    }
}
