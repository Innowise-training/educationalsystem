package com.innowise.educationalsystem.subscription.mapper;

import com.innowise.educationalsystem.subscription.dto.ConnectionUrlDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ConnectionUrlMapper {
    ConnectionUrlDto rawConnectionUrlToDto(String connectionUrl);
}
