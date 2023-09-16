package com.innowise.educationalsystem.document;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.innowise.educationalsystem.document.task.TranslationTask;
import com.innowise.educationalsystem.dto.temp.TypeDto;
import com.innowise.educationalsystem.factory.subtype.SubtypeFactory;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@Document(collection = "tasks")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "name")
@JsonSubTypes(@JsonSubTypes.Type(value = TranslationTask.class, name = "Translation"))
public abstract class Task<IN, OUT> {
    @Id
    protected String id;

    protected IN in;

    protected OUT out;

    protected Subtype<?> subtype;

    @Field(name = "_class")
    protected String typeName;

    protected Task(TypeDto typeDto, SubtypeFactory subtypeFactory) {
        in = (IN) typeDto.getIn();
        out = (OUT) typeDto.getOut();
        subtype = subtypeFactory.provideSubtype(typeDto.getSubtype());
    }
}