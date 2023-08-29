package com.innowise.educationalsystem.mapper;

import com.innowise.educationalsystem.domain.Payment;
import com.innowise.educationalsystem.dto.PaymentDto;
import com.innowise.educationalsystem.dto.PaymentRequestDto;
import com.innowise.educationalsystem.dto.PaymentApiDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PaymentMapper {
    Payment mapToEntity(PaymentRequestDto paymentRequestDto);

    PaymentDto mapToDto(Payment payment);

    PaymentApiDto mapToApiDto(Payment payment);
}
