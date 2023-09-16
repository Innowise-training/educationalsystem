package com.innowise.educationalsystem.document;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.innowise.educationalsystem.document.subtype.translation.impl.PuzzleSubtype;
import com.innowise.educationalsystem.dto.temp.SubtypeDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "name")
@JsonSubTypes(@JsonSubTypes.Type(value = PuzzleSubtype.class, name = "Puzzle"))
public abstract class Subtype<ADD> {
    protected ADD additionalData;

    @Field(name = "_class")
    protected String subtypeName;

    protected Subtype(ADD additionalData) {
        this.additionalData = additionalData;
    }

    protected Subtype(SubtypeDto subtypeDto) {
        additionalData = (ADD) subtypeDto.getAdd();
    }
}


