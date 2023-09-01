package com.innowise.educationalsystem.course.kafka;

import com.innowise.educationalsystem.course.dto.AttemptRequest;
import com.innowise.educationalsystem.course.dto.AttemptResponse;
import com.innowise.educationalsystem.course.dto.MailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEmailConsumer {
    private static final String KAFKA_MAIL_RESPONSE_TOPIC = "${app.kafka.topics.mail-response}";
    private static final String KAFKA_ATTEMPT_RESPONSE_TOPIC = "${app.kafka.topics.attempt-response}";
    private static final String KAFKA_ATTEMPT_REQUEST_TOPIC = "${app.kafka.topics.attempt-request}";
    private static final String KAFKA_MAIL_GROUP_ID = "${app.kafka.groups.mail}";

    @SendTo(KAFKA_ATTEMPT_REQUEST_TOPIC)
    @KafkaListener(topics = KAFKA_MAIL_RESPONSE_TOPIC, groupId = KAFKA_MAIL_GROUP_ID, containerFactory = "mailResponseContainerFactory")
    public AttemptRequest mailResponseListener(@Payload MailResponse mailResponse, Acknowledgment acknowledgment) {
        log.info("Message {} received from Kafka", mailResponse);
        // TODO: Exception handling if repose status is NOT SUCCESSFUL
        acknowledgment.acknowledge();
        return AttemptRequest.builder() // TODO: When override payload ???
                .mailId(mailResponse.getMailId())
                .build();
    }

    @KafkaListener(topics = KAFKA_ATTEMPT_RESPONSE_TOPIC, groupId = KAFKA_MAIL_GROUP_ID, containerFactory = "mailAttemptContainerFactory")
    public void attemptResponseListener(@Payload AttemptResponse attemptResponse, Acknowledgment acknowledgment) {
        log.info("Message {} received from Kafka", attemptResponse);
        // TODO: Exception handling if repose status is NOT SUCCESSFUL
        acknowledgment.acknowledge();
        log.info("Email id: {} successfully send to user", attemptResponse.getMailId());
    }
}
