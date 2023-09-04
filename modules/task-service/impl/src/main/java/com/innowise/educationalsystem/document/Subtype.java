package com.innowise.educationalsystem.document;

import com.innowise.educationalsystem.dto.SubtypeDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
public abstract class Subtype<ADD> {
    ADD additionalData;

    @Field(name = "_class")
    protected String subtypeName;

    protected Subtype(SubtypeDto subtypeDto) {
        additionalData = (ADD) subtypeDto.getAdd();
    }
}


