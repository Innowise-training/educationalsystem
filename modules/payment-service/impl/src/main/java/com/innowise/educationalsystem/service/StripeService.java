package com.innowise.educationalsystem.service;

import com.innowise.educationalsystem.domain.PaymentStatus;
import com.innowise.educationalsystem.dto.PaymentDto;

// TEST Interface
public interface StripeService {
    String createPaymentSession(PaymentDto paymentDto);

    PaymentStatus updatePaymentStatus(String payload, String signature);
}
