package com.innowise.educationalsystem.client.impl;

import com.innowise.educationalsystem.client.NotificationClient;
import com.innowise.educationalsystem.dto.mail.*;
import com.innowise.educationalsystem.entity.Invite;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.testcontainers.shaded.com.google.common.primitives.Longs;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailNotificationClient implements NotificationClient {
    private static final String SUBSCRIPTION_ID_HEADER = "subscription_id";
    private static final String MAIL_TYPE = "INVITE";

    @Value("${kafka.attempt.max-count:10}")
    private Integer maxRetryCount;

    @Value("${kafka.topic.mail-request}")
    private String mailRequestTopic;

    @Value("${kafka.topic.attempt-request}")
    private String attemptRequestTopic;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final Map<String, Invite> inviteMap = new HashMap<>();
    private final Map<String, Integer> attemptsCountMap = new HashMap<>();

    @Override
    public void sendInvite(Invite invite) {
        inviteMap.put(invite.getId(), invite);
        attemptsCountMap.put(invite.getId(), 0);
        sendMailRequest(invite);
    }

    @KafkaListener(topics = "${kafka.topic.mail-response}",
            containerFactory = "kafkaListenerContainerFactory")
    private void listenMailResponse(
            @Payload MailResponse mailResponse,
            @Header(KafkaHeaders.CORRELATION_ID) String correlationId,
            Acknowledgment acknowledgment) {
        Invite invite = inviteMap.get(correlationId);
        if (invite == null) {
            return;
        }

        if (mailResponse.getCreationStatus() == MailStatus.FAILED) {
            int attemptsCount = attemptsCountMap.get(correlationId);
            if (attemptsCount <= maxRetryCount) {
                sendMailRequest(invite);
                attemptsCountMap.put(correlationId, ++attemptsCount);
            } else {
                clearMaps(correlationId);
            }
        } else if (mailResponse.getCreationStatus() == MailStatus.SUCCESSFUL) {
            sendAttemptRequest(mailResponse.getMailId(), invite);
        }

        acknowledgment.acknowledge();
    }

    @KafkaListener(topics = "${kafka.topic.attempt-response}",
            containerFactory = "kafkaListenerContainerFactory")
    private void listenAttemptResponse(
            @Payload AttemptResponse attemptResponse,
            @Header(KafkaHeaders.CORRELATION_ID) String correlationId,
            Acknowledgment acknowledgment) {
        Invite invite = inviteMap.get(correlationId);
        if (invite == null) {
            return;
        }

        if (attemptResponse.getAttemptStatus() == MailStatus.FAILED) {
            int attemptsCount = attemptsCountMap.get(correlationId);
            if (attemptsCount <= maxRetryCount) {
                sendAttemptRequest(attemptResponse.getMailId(), invite);
                attemptsCountMap.put(correlationId, ++attemptsCount);
            } else {
                clearMaps(correlationId);
            }
        } else if (attemptResponse.getAttemptStatus() == MailStatus.SUCCESSFUL) {
            log.info("Sent invite with id {} to notification service", invite.getId());
            clearMaps(correlationId);
        }

        acknowledgment.acknowledge();
    }

    private void sendMailRequest(Invite invite) {
        MailRequest mailRequest = new MailRequest();
        mailRequest.setMailType(MAIL_TYPE);
        mailRequest.setDestinationEmail(invite.getEmail());
        mailRequest.setPayload(Collections.singletonMap("inviteId", invite.getId()));

        sendRequest(mailRequestTopic, mailRequest, invite);
    }

    private void sendAttemptRequest(String mailId, Invite invite) {
        AttemptRequest attemptRequest = new AttemptRequest();
        attemptRequest.setMailId(mailId);
        sendRequest(attemptRequestTopic, attemptRequest, invite);
    }

    private void sendRequest(String topic, Object value, Invite invite) {
        ProducerRecord<String, Object> producerRecord = new ProducerRecord<>(topic, value);
        producerRecord.headers().add(SUBSCRIPTION_ID_HEADER, Longs.toByteArray(invite.getSubscriptionId()));
        producerRecord.headers().add(KafkaHeaders.CORRELATION_ID, invite.getId().getBytes());
        kafkaTemplate.send(producerRecord);
    }

    private void clearMaps(String correlationId) {
        inviteMap.remove(correlationId);
        attemptsCountMap.remove(correlationId);
    }
}
