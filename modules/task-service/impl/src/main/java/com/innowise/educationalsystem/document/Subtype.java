package com.innowise.educationalsystem.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "subtype")
public abstract class Subtype {
    @Id
    private String id;
}
