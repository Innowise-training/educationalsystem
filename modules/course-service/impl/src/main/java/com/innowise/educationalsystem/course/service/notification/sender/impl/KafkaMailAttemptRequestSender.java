package com.innowise.educationalsystem.course.service.notification.sender.impl;

import com.innowise.educationalsystem.course.dto.AttemptRequest;
import com.innowise.educationalsystem.course.service.notification.address.impl.KafkaSingleTopicNotificationAddress;
import com.innowise.educationalsystem.course.service.notification.payload.impl.MailAttemptRequestPayload;
import com.innowise.educationalsystem.course.service.notification.sender.NotificationSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;


@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaMailAttemptRequestSender implements NotificationSender<MailAttemptRequestPayload, KafkaSingleTopicNotificationAddress> {
    private final KafkaTemplate<String, AttemptRequest> mailAttemptRequestKafkaTemplate;

    @Override
    public void send(MailAttemptRequestPayload payload, KafkaSingleTopicNotificationAddress address) {
        final AttemptRequest attemptRequest = payload.notificationContent().content();

        ProducerRecord<String, AttemptRequest> producerRecord = new ProducerRecord<>(address.addressDetails().details(), attemptRequest);
        producerRecord.headers().add(KafkaHeaders.CORRELATION_ID, attemptRequest.getMailId().getBytes());

        ListenableFuture<SendResult<String, AttemptRequest>> response = mailAttemptRequestKafkaTemplate.send(producerRecord);

        response.completable()
                .whenComplete((data, ex) -> {
                    if (ex == null) {
                        log.info("Message send K: {}, V: {}", data.getProducerRecord().key(), data.getProducerRecord().value());
                    } else {
                        log.error("Error during sending payload `K: {}, V: {}` to Kafka",
                                data.getProducerRecord().key(), data.getProducerRecord().value());
                    }
                });
    }
}
