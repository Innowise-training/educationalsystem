package com.innowise.educationalsystem.dto.task;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.innowise.educationalsystem.dto.NewSubtypeDto;
import com.innowise.educationalsystem.dto.TaskDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@JsonTypeName("Translation")
@EqualsAndHashCode(callSuper = true)
public class TranslationDto<T extends NewSubtypeDto<?>> extends TaskDto<String, List<String>, T> {
}
