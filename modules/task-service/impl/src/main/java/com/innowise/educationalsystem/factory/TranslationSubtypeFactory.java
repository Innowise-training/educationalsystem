package com.innowise.educationalsystem.factory;

import com.innowise.educationalsystem.dto.SubtypeDto;
import com.innowise.educationalsystem.entity.PuzzleSubtype;
import com.innowise.educationalsystem.entity.TranslationSubtype;

public class TranslationSubtypeFactory {

    public TranslationSubtype<?> provideSubtype(SubtypeDto subtypeDto) {

        switch (subtypeDto.getName()) {
            case "puzzle": return new PuzzleSubtype(subtypeDto);
        }

        throw new RuntimeException();
    }
}
