package com.innowise.educationalsystem.course.kafka;

import com.innowise.educationalsystem.course.config.KafkaConfigProperties;
import com.innowise.educationalsystem.course.dto.AttemptRequest;
import com.innowise.educationalsystem.course.dto.MailRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaEmailProducer {
    private final KafkaTemplate<String, MailRequest> mailRequestKafkaTemplate;

    private final KafkaTemplate<String, AttemptRequest> attemptRequestKafkaTemplate;

    private final KafkaConfigProperties kafkaConfigProperties;

    private static final String KAFKA_MAIL_REQUEST_TOPIC_PROPERTY = "mail-request";
    private static final String KAFKA_MAIL_ATTEMPT_TOPIC_PROPERTY = "attempt-request";

    public void sendEmailRequestToKafka(MailRequest mailRequest) {
        ListenableFuture<SendResult<String, MailRequest>> response = mailRequestKafkaTemplate.send(
                kafkaConfigProperties.getTopics().get(KAFKA_MAIL_REQUEST_TOPIC_PROPERTY),
                UUID.randomUUID().toString(),
                mailRequest);

        response.completable()
                .whenComplete((data, ex) -> {
                    if (ex == null) {
                        log.info("Message send K: {}, V: {}", data.getProducerRecord().key(), data.getProducerRecord().value());
                    } else {
                        log.error("Error during sending message `K: {}, V: {}` to Kafka",
                                data.getProducerRecord().key(), data.getProducerRecord().value());
                    }
                });
    }

    public void sendEmailSendingAttemptToKafka(AttemptRequest attemptRequest) {
        ListenableFuture<SendResult<String, AttemptRequest>> response = attemptRequestKafkaTemplate.send(
                kafkaConfigProperties.getTopics().get(KAFKA_MAIL_ATTEMPT_TOPIC_PROPERTY),
                UUID.randomUUID().toString(),
                attemptRequest);

        response.completable()
                .whenComplete((data, ex) -> {
                    if (ex == null) {
                        log.info("Message send K: {}, V: {}", data.getProducerRecord().key(), data.getProducerRecord().value());
                    } else {
                        log.error("Error during sending message `K: {}, V: {}` to Kafka",
                                data.getProducerRecord().key(), data.getProducerRecord().value());
                    }
                });
    }
}
