package com.innowise.educationalsystem.service;

import com.innowise.educationalsystem.domain.Payment;
import com.innowise.educationalsystem.domain.PaymentStatus;

public interface PaymentService {
    Payment initPayment(Payment payment);

    Payment proceedPaymentStatus(long paymentId, PaymentStatus status);

    Payment finById(long id);

}
