package com.innowise.educationalsystem.config;

import com.innowise.educationalsystem.dto.AttemptRequest;
import com.innowise.educationalsystem.dto.AttemptResponse;
import com.innowise.educationalsystem.util.MailKafkaHeaders;
import com.innowise.educationalsystem.dto.MailRequest;
import com.innowise.educationalsystem.dto.MailResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;

@Slf4j
@EnableKafka
@Configuration
@RequiredArgsConstructor
@PropertySource(value = "classpath:kafka-props.yaml", factory = YamlPropertySourceFactory.class)
public class KafkaConsumerConfig {
    private final KafkaPropertiesConfig kafkaPropertiesConfig;

    @Value("${kafka.consumer.mail.default-group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, MailRequest> mailRequestConsumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaPropertiesConfig.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.innowise.educationalsystem.*");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, MailRequest.class);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MailRequest> mailRequestKafkaListenerContainerFactory(
        KafkaTemplate<String, MailResponse> mailResponseKafkaTemplate) {

        ConcurrentKafkaListenerContainerFactory<String, MailRequest> factory =
            new ConcurrentKafkaListenerContainerFactory<>();

        factory.setConsumerFactory(mailRequestConsumerFactory());
        factory.setReplyTemplate(mailResponseKafkaTemplate);
        factory.setReplyHeadersConfigurer((k, v) -> k.equals(MailKafkaHeaders.SUBSCRIPTION_ID));
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        return factory;
    }

    @Bean
    public ConsumerFactory<String, AttemptRequest> attemptRequestConsumerFactory() {
        Map<String, Object> props = new HashMap<>();

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaPropertiesConfig.getBootstrapServers());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.innowise.educationalsystem.*");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, AttemptRequest.class);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AttemptRequest> attemptRequestKafkaListenerContainerFactory(
        KafkaTemplate<String, AttemptResponse> attemptResponseKafkaTemplate) {

        ConcurrentKafkaListenerContainerFactory<String, AttemptRequest> factory =
            new ConcurrentKafkaListenerContainerFactory<>();

        factory.setReplyTemplate(attemptResponseKafkaTemplate);
        factory.setReplyHeadersConfigurer((k, v) -> k.equals(MailKafkaHeaders.SUBSCRIPTION_ID));
        factory.setConsumerFactory(attemptRequestConsumerFactory());
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);

        return factory;
    }
}
