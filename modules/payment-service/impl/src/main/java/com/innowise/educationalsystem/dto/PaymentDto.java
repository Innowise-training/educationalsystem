package com.innowise.educationalsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.innowise.educationalsystem.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "payment")
public class PaymentDto {
    private Long id;

    private String name;

    private String description;

    private Long amount;

    private PaymentStatus status;

    private String paymentMethod;

    private String currency;

    @JsonIgnore
    private String callBackUri;

    private long subscriptionId;

    private LocalDate paymentStartedAt;

    private LocalDate paymentUpdatedAt;
}
