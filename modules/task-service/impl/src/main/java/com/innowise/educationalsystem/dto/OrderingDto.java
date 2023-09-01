package com.innowise.educationalsystem.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class OrderingDto extends TypeDto {
    private Set<String> words;

    private List<String> correctAnswers;
}
