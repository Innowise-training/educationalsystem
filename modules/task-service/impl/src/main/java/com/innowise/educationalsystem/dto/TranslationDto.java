package com.innowise.educationalsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class TranslationDto extends TypeDto {
    private String translatable;

    private List<String> correctAnswers;
}
