package com.innowise.educationalsystem.document;

import com.innowise.educationalsystem.dto.TypeDto;
import com.innowise.educationalsystem.factory.subtype.SubtypeFactory;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@Document(collection = "tasks")
public abstract class Task<IN, OUT, T extends Subtype> {
    @Id
    protected String id;

    protected IN in;

    protected OUT out;

    protected T subtype;

    @Field(name = "_class")
    protected String typeName;

    protected Task(TypeDto typeDto, SubtypeFactory subtypeFactory) {
        in = (IN) typeDto.getIn();
        out = (OUT) typeDto.getOut();
        subtype = (T) subtypeFactory.provideSubtype(typeDto.getSubtype());
    }
}


