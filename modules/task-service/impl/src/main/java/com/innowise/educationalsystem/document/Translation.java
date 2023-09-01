package com.innowise.educationalsystem.document;


import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Builder
@Document(collection = "type")
@EqualsAndHashCode(callSuper = true)
public class Translation extends Type {
    private String translatable;

    private List<String> correctAnswers;
}
