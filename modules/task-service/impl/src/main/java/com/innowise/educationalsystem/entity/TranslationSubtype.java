package com.innowise.educationalsystem.entity;

import com.innowise.educationalsystem.dto.SubtypeDto;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
public abstract class TranslationSubtype<ADD> extends Subtype<ADD> {

    protected TranslationSubtype(SubtypeDto subtypeDto) {
        super(subtypeDto);
    }
}


