package com.innowise.educationalsystem.course.kafka;

import com.innowise.educationalsystem.course.config.KafkaConfigProperties;
import com.innowise.educationalsystem.course.dto.AttemptRequest;
import com.innowise.educationalsystem.course.dto.AttemptResponse;
import com.innowise.educationalsystem.course.dto.MailResponse;
import com.innowise.educationalsystem.course.dto.enums.AttemptStatus;
import com.innowise.educationalsystem.course.dto.enums.CreationStatus;
import com.innowise.educationalsystem.course.service.NotificationService;
import com.innowise.educationalsystem.course.service.RetryableService;
import com.innowise.educationalsystem.course.service.notification.address.NotificationAddress;
import com.innowise.educationalsystem.course.service.notification.address.impl.KafkaSingleTopicNotificationAddress;
import com.innowise.educationalsystem.course.service.notification.impl.SimpleNotification;
import com.innowise.educationalsystem.course.service.notification.payload.NotificationPayload;
import com.innowise.educationalsystem.course.service.notification.payload.impl.MailAttemptRequestPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEmailConsumer {

    @Autowired
    @Qualifier("retryNotificationService")
    private final NotificationService notificationService;

    private final RetryableService retryableService;

    private final KafkaConfigProperties kafkaConfigProperties;

    private static final String KAFKA_MAIL_RESPONSE_TOPIC = "${app.kafka.topics.mail-response}";
    private static final String KAFKA_ATTEMPT_RESPONSE_TOPIC = "${app.kafka.topics.attempt-response}";
    private static final String KAFKA_ATTEMPT_REQUEST_TOPIC_PROPERTY = "attempt-request";
    private static final String KAFKA_MAIL_GROUP_ID = "${app.kafka.groups.mail}";

    @KafkaListener(topics = KAFKA_MAIL_RESPONSE_TOPIC, groupId = KAFKA_MAIL_GROUP_ID, containerFactory = "mailResponseContainerFactory")
    public void mailResponseListener(
            @Header(KafkaHeaders.CORRELATION_ID) String correlationId,
            @Payload MailResponse mailResponse,
            Acknowledgment acknowledgment) {
        log.info("Message {} received from Kafka", mailResponse);

        acknowledgment.acknowledge();

        if (!retryableService.isRetryableNotification(correlationId)) {
            return;
        }

        if (mailResponse.getCreationStatus().equals(CreationStatus.FAILED)) {
            retryableService.retry(correlationId);
            return;
        }

        AttemptRequest attemptRequest = AttemptRequest.builder() // TODO: When override payload ???
                .mailId(mailResponse.getMailId())
                .build();
        NotificationPayload mailAttemptRequestPayload = new MailAttemptRequestPayload(attemptRequest);
        NotificationAddress mailAttemptRequestAddress = new KafkaSingleTopicNotificationAddress(kafkaConfigProperties
                .getTopics().get(KAFKA_ATTEMPT_REQUEST_TOPIC_PROPERTY));

        notificationService.sendNotification(new SimpleNotification(attemptRequest.getMailId(), mailAttemptRequestPayload, mailAttemptRequestAddress));
    }

    @KafkaListener(topics = KAFKA_ATTEMPT_RESPONSE_TOPIC, groupId = KAFKA_MAIL_GROUP_ID, containerFactory = "mailAttemptContainerFactory")
    public void attemptResponseListener(
            @Header(KafkaHeaders.CORRELATION_ID) String correlationId,
            @Payload AttemptResponse attemptResponse,
            Acknowledgment acknowledgment) {
        log.info("Message {} received from Kafka", attemptResponse);

        acknowledgment.acknowledge();

        if (!retryableService.isRetryableNotification(correlationId)) {
            return;
        }

        if (attemptResponse.getAttemptStatus().equals(AttemptStatus.FAILED)) {
            retryableService.retry(correlationId);
            return;
        }

        log.info("Email id: {} successfully send to user", attemptResponse.getMailId());
    }
}
