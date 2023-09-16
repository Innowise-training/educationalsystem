package com.innowise.educationalsystem.dto.task;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.innowise.educationalsystem.dto.TaskDto;
import com.innowise.educationalsystem.dto.subtype.translation.TranslationSubtype;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@JsonTypeName("Translation")
@EqualsAndHashCode(callSuper = true)
public class TranslationDto extends TaskDto<String, List<String>> {
    protected TranslationSubtype<?> subtype;
}
