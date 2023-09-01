package com.innowise.educationalsystem.mapper;

import com.innowise.educationalsystem.document.Writing;
import com.innowise.educationalsystem.dto.WritingDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WritingMapper {
    WritingDto toDto(Writing writing);

    Writing toWriting(WritingDto writingDto);
}
