package com.innowise.educationalsystem.document;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Data
@Builder
@Document(collection = "type")
@EqualsAndHashCode(callSuper = true)
public class Ordering extends Type {
    private Set<String> words;

    private List<String> correctAnswers;
}
