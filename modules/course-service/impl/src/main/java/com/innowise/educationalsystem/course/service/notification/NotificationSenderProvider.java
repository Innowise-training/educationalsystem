package com.innowise.educationalsystem.course.service.notification;

import com.innowise.educationalsystem.course.service.notification.sender.NotificationSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.ParameterizedType;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationSenderProvider {
    private final ApplicationContext applicationContext;

    public <P, A> NotificationSender<P, A> getSenderByType(Class<? extends P> payloadClass, Class<? extends A> addressClass) {
        Map<String, NotificationSender> beansOfType = applicationContext.getBeansOfType(NotificationSender.class);

        for (NotificationSender<?, ?> bean : beansOfType.values()) {
            if (bean.getClass().getGenericInterfaces().length > 0) {
                java.lang.reflect.Type[] actualTypeArguments = ((ParameterizedType) bean.getClass()
                        .getGenericInterfaces()[0]).getActualTypeArguments();
                if (actualTypeArguments.length == 2 &&
                        actualTypeArguments[0].getTypeName().equals(payloadClass.getName()) &&
                        actualTypeArguments[1].getTypeName().equals(addressClass.getName())) {
                    return (NotificationSender<P, A>) bean;
                }
            }
        }

        throw new RuntimeException("Bean `NotificationSender` not found for payloadClass:" + payloadClass.getSimpleName() + " and addressClass " + addressClass.getSimpleName());
    }
}
