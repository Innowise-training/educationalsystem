package com.innowise.educationalsystem.listener;

import com.innowise.educationalsystem.dto.AttemptRequest;
import com.innowise.educationalsystem.dto.AttemptResponse;
import com.innowise.educationalsystem.dto.MailRequest;
import com.innowise.educationalsystem.dto.MailResponse;
import com.innowise.educationalsystem.service.AttemptService;
import com.innowise.educationalsystem.service.MailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MailApiListener {

    private final MailService mailService;
    private final AttemptService attemptService;

    @KafkaListener(topics = "${kafka.topic.mail-request}", containerFactory = "mailRequestKafkaListenerContainerFactory")
    @SendTo("${kafka.topic.mail-response}")
    public MailResponse listenMailRequests(@Payload MailRequest mailRequest, Acknowledgment acknowledgment) {

        log.info("Received message {}", mailRequest);
        MailResponse mailResponse = mailService.createMail(mailRequest);
        acknowledgment.acknowledge();
        return mailResponse;
    }

    @KafkaListener(topics = "${kafka.topic.attempt-request}", containerFactory = "attemptRequestKafkaListenerContainerFactory")
    @SendTo("${kafka.topic.attempt-response}")
    public AttemptResponse listenAttemptRequests(@Payload AttemptRequest attemptRequest, Acknowledgment acknowledgment) {

        log.info("Received message {}", attemptRequest);
        AttemptResponse attemptResponse = attemptService.mailAttempt(attemptRequest);
        acknowledgment.acknowledge();
        return attemptResponse;
    }
}
