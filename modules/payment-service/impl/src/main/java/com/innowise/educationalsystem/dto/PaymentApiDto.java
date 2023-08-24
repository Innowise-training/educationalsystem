package com.innowise.educationalsystem.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.innowise.educationalsystem.domain.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentApiDto {
    private long id;

    private long subscriptionId;

    @JsonIgnore
    private String callBackUri;

    private PaymentStatus status;
}
