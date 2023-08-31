package com.innowise.educationalsystem.controller;

import com.innowise.educationalsystem.domain.Payment;
import com.innowise.educationalsystem.domain.PaymentStatus;
import com.innowise.educationalsystem.dto.PaymentApiDto;
import com.innowise.educationalsystem.dto.PaymentDto;
import com.innowise.educationalsystem.dto.PaymentRequestDto;
import com.innowise.educationalsystem.mapper.PaymentMapper;
import com.innowise.educationalsystem.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final PaymentService paymentService;

    private final PaymentMapper paymentMapper;

    /**
     * @param paymentRequestDto - payment metadata to init {@link Payment} entity.
     * @return created payment id
     */
    @PostMapping
    @PreAuthorize("hasAuthority('PAYMENT_INIT')")
    public PaymentDto initPayment(@RequestBody @Valid PaymentRequestDto paymentRequestDto) {
        Payment payment = paymentMapper.mapToEntity(paymentRequestDto);
        Payment initializedPayment = paymentService.initPayment(payment);
        return paymentMapper.mapToDto(initializedPayment);
    }

    /**
     * @param id     - id of a payment
     * @param status - status we wanna change for our payment
     * @return {@link PaymentApiDto} with updated status of a payment
     */
    @PostMapping("{id}/{status}")
    @PreAuthorize("hasAuthority('PAYMENT_PROCEED_STATUS')")
    public PaymentApiDto proceedPaymentStatus(@PathVariable("id") long id, @PathVariable("status") PaymentStatus status) {
        Payment payment = paymentService.proceedPaymentStatus(id, status);
        return paymentMapper.mapToApiDto(payment);
    }

    /**
     * Test method to receive a payment status (after it, we will be fulfilled in webhook endpoint)
     * After will send repose to callbackUri for a service that request a payment
     *
     * @return paymentApiDto
     */
    //TODO: Using OpenFeign | Kafka ??? send a request to callBackUri (it's a service url thar requested a payment)
    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('PAYMENT_GET_STATUS')")
    public PaymentDto findById(@PathVariable("id") long id) {
        // TODO: make a callback for URI in paymentRequest inside a service method. It will be used in WebHook or some other endpoint we interact with payment integration system
        Payment payment = paymentService.finById(id);
        return paymentMapper.mapToDto(payment);
    }
}
