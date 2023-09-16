package com.innowise.educationalsystem.document.subtype.ordering;

import com.innowise.educationalsystem.dto.temp.SubtypeDto;
import com.innowise.educationalsystem.document.Subtype;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
public abstract class OrderingSubtype<ADD> extends Subtype<ADD> {
    protected OrderingSubtype() {
        super();
    }

    protected OrderingSubtype(SubtypeDto subtypeDto) {
        super(subtypeDto);
    }
}
