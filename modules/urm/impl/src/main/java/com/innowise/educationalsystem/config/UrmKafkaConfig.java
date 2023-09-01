package com.innowise.educationalsystem.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class UrmKafkaConfig {
    private static final String URM_PACKAGE = "com.innowise.educationalsystem.dto.mail";
    private static final String EMAIL_PACKAGE = "com.innowise.educationalsystem.dto";

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id:urm-group}")
    private String groupId;

    @Bean
    public ProducerFactory<String, Object> defaultKafkaProducerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        properties.put(JsonSerializer.TYPE_MAPPINGS, String.format(
                "%s.MailRequest:%s.MailRequest,%s.AttemptRequest:%s.AttemptRequest",
                EMAIL_PACKAGE, URM_PACKAGE, EMAIL_PACKAGE, URM_PACKAGE));

        return new DefaultKafkaProducerFactory<>(properties);
    }

    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return new KafkaTemplate<>(defaultKafkaProducerFactory());
    }

    @Bean
    public ConsumerFactory<String, Object> kafkaConsumerFactory() {
        Map<String, Object> properties = new HashMap<>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        properties.put(JsonDeserializer.TRUSTED_PACKAGES, "com.innowise.educationalsystem.*");
        properties.put(JsonDeserializer.TYPE_MAPPINGS, String.format(
                "%s.MailResponse:%s.MailResponse,%s.AttemptResponse:%s.AttemptResponse",
                EMAIL_PACKAGE, URM_PACKAGE, EMAIL_PACKAGE, URM_PACKAGE));

        return new DefaultKafkaConsumerFactory<>(properties);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(kafkaConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        return factory;
    }
}
