package com.innowise.educationalsystem.entity;

import com.innowise.educationalsystem.dto.TypeDto;
import com.innowise.educationalsystem.factory.TranslationSubtypeFactory;
import java.util.List;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * {"in": "Hello world", "out": ["Privet mir"], "name": "Trans", "subtype": {"name": "puzzle", "add": ["goodbye"]}}
 */

@Document(collection = "tasks")
public class TranslationTask<T extends TranslationSubtype> extends Task<String, List<String>, T> {

    public TranslationTask(TypeDto typeDto) {
        super(typeDto);
        TranslationSubtypeFactory factory = new TranslationSubtypeFactory();
        subtype = (T) factory.provideSubtype(typeDto.getSubtype());
    }
}

