package com.innowise.educationalsystem.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.innowise.educationalsystem.dto.subtype.translation.impl.PuzzleSubtypeDto;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "name")
@JsonSubTypes(@JsonSubTypes.Type(value = PuzzleSubtypeDto.class, name = "Puzzle"))
public abstract class NewSubtypeDto<ADD> {
    protected ADD additionalData;
}
