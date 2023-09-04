package com.innowise.educationalsystem.document.subtype.translation.impl;

import com.innowise.educationalsystem.dto.SubtypeDto;
import com.innowise.educationalsystem.document.subtype.translation.TranslationSubtype;
import org.springframework.data.annotation.TypeAlias;

import java.util.List;

@TypeAlias("Puzzle")
public class PuzzleSubtype extends TranslationSubtype<List<String>> {

    public PuzzleSubtype() {
        super();
    }

    public PuzzleSubtype(SubtypeDto subtypeDto) {
        super(subtypeDto);
    }
}
