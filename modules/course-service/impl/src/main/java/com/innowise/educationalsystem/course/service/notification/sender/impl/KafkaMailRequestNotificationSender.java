package com.innowise.educationalsystem.course.service.notification.sender.impl;

import com.innowise.educationalsystem.course.dto.MailRequest;
import com.innowise.educationalsystem.course.service.notification.address.impl.KafkaSingleTopicNotificationAddress;
import com.innowise.educationalsystem.course.service.notification.payload.impl.MailRequestPayload;
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
public class KafkaMailRequestNotificationSender implements NotificationSender<MailRequestPayload, KafkaSingleTopicNotificationAddress> {
    private final KafkaTemplate<String, MailRequest> mailRequestKafkaTemplate;

    @Override
    public void send(MailRequestPayload payload, KafkaSingleTopicNotificationAddress address) {
        final MailRequest mailRequest = payload.notificationContent().content();

        ProducerRecord<String, MailRequest> producerRecord = new ProducerRecord<>(address.addressDetails().details(), mailRequest);
        producerRecord.headers().add(KafkaHeaders.CORRELATION_ID, mailRequest.getDestinationEmail().getBytes());

        ListenableFuture<SendResult<String, MailRequest>> response = mailRequestKafkaTemplate.send(producerRecord);

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
