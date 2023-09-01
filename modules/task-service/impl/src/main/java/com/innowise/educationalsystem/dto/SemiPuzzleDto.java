package com.innowise.educationalsystem.dto;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class SemiPuzzleDto {
    private List<String> template;

    private Set<String> words;
}
