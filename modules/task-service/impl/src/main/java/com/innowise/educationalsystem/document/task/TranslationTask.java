package com.innowise.educationalsystem.document.task;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.innowise.educationalsystem.document.Task;
import com.innowise.educationalsystem.document.subtype.translation.TranslationSubtype;
import com.innowise.educationalsystem.dto.temp.TypeDto;
import com.innowise.educationalsystem.factory.subtype.SubtypeFactory;

import java.util.List;

import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * {"in": "Hello world", "out": ["Privet mir"], "name": "Trans", "subtype": {"name": "puzzle", "add": ["goodbye"]}}
 */

@TypeAlias("Translation")
@JsonTypeName("Translation")
@Document(collection = "tasks")
public class TranslationTask extends Task<String, List<String>> {
    public TranslationTask() {
        super();
    }

    public TranslationTask(TypeDto typeDto, SubtypeFactory subtypeFactory) {
        super(typeDto, subtypeFactory);
    }
}

