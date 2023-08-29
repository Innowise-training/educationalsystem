package com.innowise.educationalsystem.listener;

import com.stripe.Stripe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static com.innowise.educationalsystem.constant.StripeConstant.STRIPE_SECRET_KEY_PROPERTY;

@Slf4j
@Component
public class ContextListener {
    @Value(value = STRIPE_SECRET_KEY_PROPERTY)
    private String stripeSecretKey;

    @EventListener
    public void handleContextStartedEvent(ContextRefreshedEvent event) {
        Stripe.apiKey = stripeSecretKey;
        log.info("Stripe Api-key configured.");
    }
}
