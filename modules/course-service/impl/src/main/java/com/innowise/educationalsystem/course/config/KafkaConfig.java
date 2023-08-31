package com.innowise.educationalsystem.course.config;

import com.innowise.educationalsystem.course.dto.AttemptRequest;
import com.innowise.educationalsystem.course.dto.AttemptResponse;
import com.innowise.educationalsystem.course.dto.MailRequest;
import com.innowise.educationalsystem.course.dto.MailResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.var;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableScheduling
@EnableConfigurationProperties(value = KafkaConfigProperties.class)
public class KafkaConfig {
    private static final String MAIL_ORDER_GROUP_KEY = "mail";

    private final KafkaConfigProperties kafkaConfigProperties;

    private final KafkaProperties kafkaProperties;

    @Bean
    public ProducerFactory<String, MailRequest> mailRequestProducerFactory() {
        Map<String, Object> props = kafkaProperties.buildProducerProperties();
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.TYPE_MAPPINGS, "com.innowise.educationalsystem.dto.MailRequest:com.innowise.educationalsystem.course.dto.MailRequest");

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, MailRequest> mailRequestKafkaTemplate() {
        return new KafkaTemplate<>(mailRequestProducerFactory());
    }

    @Bean
    public ProducerFactory<String, AttemptRequest> mailAttemptRequestProducerFactory() {
        Map<String, Object> props = kafkaProperties.buildProducerProperties();
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(JsonSerializer.TYPE_MAPPINGS, "com.innowise.educationalsystem.dto.AttemptRequest:com.innowise.educationalsystem.course.dto.AttemptRequest");

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, AttemptRequest> mailAttemptRequestKafkaTemplate() {
        return new KafkaTemplate<>(mailAttemptRequestProducerFactory());
    }

    @Bean
    public ConsumerFactory<String, MailResponse> mailResponseConsumerFactory(KafkaProperties kafkaProperties) {
        var props = kafkaProperties.buildConsumerProperties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfigProperties.getGroups().get(MAIL_ORDER_GROUP_KEY));
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TYPE_MAPPINGS, "com.innowise.educationalsystem.dto.MailResponse:com.innowise.educationalsystem.course.dto.MailResponse");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, MailResponse.class);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, MailResponse>> mailResponseContainerFactory(
            KafkaProperties kafkaProperties, KafkaTemplate<String, AttemptRequest> attemptRequestKafkaTemplate) {

        var factory = new ConcurrentKafkaListenerContainerFactory<String, MailResponse>();

        factory.setReplyTemplate(attemptRequestKafkaTemplate);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setConsumerFactory(mailResponseConsumerFactory(kafkaProperties));
        factory.setConcurrency(Runtime.getRuntime().availableProcessors());

        return factory;
    }

    @Bean
    public ConsumerFactory<String, AttemptResponse> mailAttemptConsumerFactory(KafkaProperties kafkaProperties) {
        var props = kafkaProperties.buildConsumerProperties();
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfigProperties.getGroups().get(MAIL_ORDER_GROUP_KEY));
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TYPE_MAPPINGS, "com.innowise.educationalsystem.dto.AttemptResponse:com.innowise.educationalsystem.course.dto.AttemptResponse");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, AttemptResponse.class);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, AttemptResponse>> mailAttemptContainerFactory(KafkaProperties kafkaProperties) {

        var factory = new ConcurrentKafkaListenerContainerFactory<String, AttemptResponse>();

        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        factory.setConsumerFactory(mailAttemptConsumerFactory(kafkaProperties));
        factory.setConcurrency(Runtime.getRuntime().availableProcessors());

        return factory;
    }
}
