package com.innowise.educationalsystem.factory.subtype.impl;

import com.innowise.educationalsystem.dto.SubtypeDto;
import com.innowise.educationalsystem.document.subtype.translation.impl.PuzzleSubtype;
import com.innowise.educationalsystem.document.subtype.translation.TranslationSubtype;
import com.innowise.educationalsystem.document.subtype.translation.impl.WritingSubtype;
import com.innowise.educationalsystem.exception.SubtypeCreationException;
import com.innowise.educationalsystem.factory.subtype.SubtypeFactory;
import org.springframework.stereotype.Component;

@Component("translationSubtypeFactory")
public class TranslationSubtypeFactory implements SubtypeFactory {

    public TranslationSubtype<?> provideSubtype(SubtypeDto subtypeDto) {

        switch (subtypeDto.getName().toLowerCase()) {
            case "puzzle": return new PuzzleSubtype(subtypeDto);
            case "writing": return new WritingSubtype(subtypeDto);
            default: throw new SubtypeCreationException("Subtype with name \"" + subtypeDto.getName() + "\" does not supported for translation type");
        }
    }
}
