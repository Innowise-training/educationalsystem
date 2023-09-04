package com.innowise.educationalsystem.document.subtype.translation.impl;

import com.innowise.educationalsystem.dto.SubtypeDto;
import com.innowise.educationalsystem.document.subtype.translation.TranslationSubtype;
import org.springframework.data.annotation.TypeAlias;

@TypeAlias("Writing")
public class WritingSubtype extends TranslationSubtype<Void> {
    public WritingSubtype() {
        super();
    }

    public WritingSubtype(SubtypeDto subtypeDto) {
        super(subtypeDto);
    }
}
