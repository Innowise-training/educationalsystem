package com.innowise.educationalsystem.document.subtype.translation.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.innowise.educationalsystem.dto.temp.SubtypeDto;
import com.innowise.educationalsystem.document.subtype.translation.TranslationSubtype;
import org.springframework.data.annotation.TypeAlias;

import java.util.List;

@TypeAlias("Puzzle")
@JsonTypeName("Puzzle")
public class PuzzleSubtype extends TranslationSubtype<List<String>> {

    public PuzzleSubtype() {
        super();
    }

    public PuzzleSubtype(SubtypeDto subtypeDto) {
        super(subtypeDto);
    }
}
