package com.innowise.educationalsystem.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.innowise.educationalsystem.dto.task.OrderingDto;
import com.innowise.educationalsystem.dto.task.TranslationDto;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "name")
@JsonSubTypes({
        @JsonSubTypes.Type(value = TranslationDto.class, name = "Translation"),
        @JsonSubTypes.Type(value = OrderingDto.class, name = "Ordering")
})
public abstract class TaskDto<IN, OUT> {
    protected IN in;

    protected OUT out;

    protected NewSubtypeDto<?> subtype;
}
