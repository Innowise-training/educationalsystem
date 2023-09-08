package com.innowise.educationalsystem.dto.task;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.innowise.educationalsystem.dto.TaskDto;
import com.innowise.educationalsystem.dto.subtype.ordering.OrderingSubtype;

import java.util.List;
import java.util.Set;

@JsonTypeName("Ordering")
public class OrderingDto extends TaskDto<Set<String>, List<String>> {
    protected OrderingSubtype<?> subtype;
}
