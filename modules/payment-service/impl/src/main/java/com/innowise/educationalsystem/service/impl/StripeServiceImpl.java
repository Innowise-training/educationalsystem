package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.domain.PaymentStatus;
import com.innowise.educationalsystem.dto.PaymentDto;
import com.innowise.educationalsystem.service.StripeService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.innowise.educationalsystem.constant.StripeConstant.STRIPE_WEBHOOK_KEY_PROPERTY;

@Slf4j
@Service
// TEST Class
public class StripeServiceImpl implements StripeService {
    @Value(value = STRIPE_WEBHOOK_KEY_PROPERTY)
    private String stripeWebHookKey;

    @Override
    @SneakyThrows({StripeException.class})
    //TODO: Add cancel uri
    //TODO: payment method
    //TODO: Create customer
    public String createPaymentSession(PaymentDto paymentDto) {
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/api/v1/payments/success")
                .setCancelUrl("http://localhost:8080/api/v1/payments/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(paymentDto.getCurrency())
                                                .setUnitAmount(paymentDto.getAmount() * 100L)
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(paymentDto.getName())
                                                                .setDescription(paymentDto.getDescription())
                                                                .build()
                                                ).build())
                                .build())
                .build();
        Session session = Session.create(params);
        log.error(session.getUrl());
        return session.getUrl();
    }

    @Override
    @SneakyThrows({SignatureVerificationException.class})
    public PaymentStatus updatePaymentStatus(String payload, String signature) {
        Event event = Webhook.constructEvent(payload, signature, stripeWebHookKey);
        String eventType = event.getType();
        //TODO: Statuses
        return null;
    }
}
