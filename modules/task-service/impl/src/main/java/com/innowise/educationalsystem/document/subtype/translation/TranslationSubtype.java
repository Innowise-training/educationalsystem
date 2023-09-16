package com.innowise.educationalsystem.document.subtype.translation;

import com.innowise.educationalsystem.document.Subtype;
import com.innowise.educationalsystem.dto.temp.SubtypeDto;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
public abstract class TranslationSubtype<ADD> extends Subtype<ADD> {

    public TranslationSubtype() {
        super();
    }

    protected TranslationSubtype(ADD additionalData) {
        super(additionalData);
    }

    protected TranslationSubtype(SubtypeDto subtypeDto) {
        super(subtypeDto);
    }
}


