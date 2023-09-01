package com.innowise.educationalsystem.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "tasks")
public class Task {
    @Id
    private String id;

    private Type type;

    private Subtype subtype;
}
