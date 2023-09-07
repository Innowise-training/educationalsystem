package com.innowise.educationalsystem.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.ObjectArrayDeserializer;
import com.innowise.educationalsystem.dto.task.TranslationDto;
import lombok.Data;

@Data
//@JsonDeserialize(using = TaskDeserializer.class)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "name")
@JsonSubTypes(@JsonSubTypes.Type(value = TranslationDto.class, name = "Translation"))
public abstract class TaskDto<IN, OUT, S extends NewSubtypeDto<?>> {
    protected IN in;

    protected OUT out;

    @JsonDeserialize(contentAs = NewSubtypeDto.class)
    protected S subtype;
}
