package com.innowise.educationalsystem.entity;

import com.innowise.educationalsystem.dto.TypeDto;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tasks")
public abstract class Task<IN, OUT, T extends Subtype> {
    IN in;

    OUT out;

    T subtype;

    protected Task(TypeDto typeDto) {
        in = (IN) typeDto.getIn();
        out = (OUT) typeDto.getOut();
    }
}


