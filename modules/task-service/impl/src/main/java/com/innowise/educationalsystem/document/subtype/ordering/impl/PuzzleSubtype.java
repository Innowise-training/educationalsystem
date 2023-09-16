package com.innowise.educationalsystem.document.subtype.ordering.impl;

import com.innowise.educationalsystem.document.subtype.ordering.OrderingSubtype;
import com.innowise.educationalsystem.dto.temp.SubtypeDto;
import org.springframework.data.annotation.TypeAlias;

import java.util.Set;

@TypeAlias("Puzzle")
public class PuzzleSubtype extends OrderingSubtype<Set<String>> {
    protected PuzzleSubtype() {
        super();
    }

    public PuzzleSubtype(SubtypeDto subtypeDto) {
        super(subtypeDto);
    }
}
