package com.innowise.educationalsystem.dto.subtype.translation.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.innowise.educationalsystem.dto.subtype.translation.TranslationSubtype;
import lombok.Data;

import java.util.Set;

@Data
@JsonTypeName("Puzzle")
public class PuzzleSubtypeDto extends TranslationSubtype<Set<String>> {
}
