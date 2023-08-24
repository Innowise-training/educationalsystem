package com.innowise.educationalsystem.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName(value = "payment")
public class PaymentRequestDto {
    @NotNull
    @Min(value = 1)
    private long subscriptionId;

    private String name;

    //TODO: validation for count
    private Long amount; // Amount in cents

    private String description;

    @NotBlank
    private String currency; // Make enum???

    @NotNull
    private String paymentMethod; // Make enum???

    @NotBlank
    private String callBackUri;

    // Additional metadata for payment
    private Map<String, String> metadata;
}
