package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.entity.Version;
import com.innowise.educationalsystem.service.MessageProvider;
import com.innowise.educationalsystem.service.SenderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SenderServiceImpl implements SenderService {
    private final MessageProvider messageProvider;

    @Override
    public void sendMail(String destEmail, Version version) {

        log.info("version {} to {} were successfully transferred", version.getVersionNumber(), destEmail);
    }
}
