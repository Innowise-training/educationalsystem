package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.domain.Payment;
import com.innowise.educationalsystem.domain.PaymentStatus;
import com.innowise.educationalsystem.dto.PaymentApiDto;
import com.innowise.educationalsystem.mapper.PaymentMapper;
import com.innowise.educationalsystem.repository.PaymentRepository;
import com.innowise.educationalsystem.service.PaymentService;
import com.innowise.educationalsystem.service.StripeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.persistence.EntityNotFoundException;
import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    private final StripeService stripeService;

    private final PaymentMapper paymentMapper;

    private final RestTemplate restTemplate;

    @Lazy
    @Autowired
    private PaymentServiceImpl self;

    @Override
    @Transactional
    public Payment initPayment(Payment payment) {
        payment.setStatus(PaymentStatus.PROCESSING);
        return paymentRepository.save(payment);
    }

    @Transactional
    public Payment updatePaymentStatusById(long paymentId, PaymentStatus status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(EntityNotFoundException::new);
        //TODO: Check if status is different
        payment.setStatus(status);
        return paymentRepository.save(payment);
    }

    @Override
    // TODO: Unique for each payment service ????
    // TODO: Think about how to retrieve a payment from Database (send some info about the payment to payment system) or retrieve by user info
    // TODO: To obtain more info - get by other endpoint
    public Payment proceedPaymentStatus(long paymentId, PaymentStatus status) {
        Payment proceededPayment = self.updatePaymentStatusById(paymentId, status);
        PaymentApiDto proceededPaymentApiDto = paymentMapper.mapToApiDto(proceededPayment);
        restTemplate.exchange(
                RequestEntity
                        .post(URI.create(proceededPayment.getCallBackUri()))
                        .accept(MediaType.APPLICATION_JSON)
                        .body(proceededPaymentApiDto),
                new ParameterizedTypeReference<Void>() {
                });
        return proceededPayment;
    }

    @Override
    @Transactional(readOnly = true)
    // TODO: Exception Handling
    public Payment finById(long id) {
        return paymentRepository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}
