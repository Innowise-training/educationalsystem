package com.innowise.educationalsystem.mapper;

import com.innowise.educationalsystem.document.Type;
import com.innowise.educationalsystem.dto.TypeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TypeMapper {
    TypeDto toDto(Type type);

}
