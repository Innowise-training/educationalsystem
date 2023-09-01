package com.innowise.educationalsystem.mapper;

import com.innowise.educationalsystem.document.Ordering;
import com.innowise.educationalsystem.dto.OrderingDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderingMapper {
    OrderingDto toDto(Ordering ordering);

    Ordering toOrdering(OrderingDto orderingDto);
}
