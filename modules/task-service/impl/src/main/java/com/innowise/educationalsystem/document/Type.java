package com.innowise.educationalsystem.document;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "type")
public abstract class Type {
    @Id
    private String id;
}
