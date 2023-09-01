package com.innowise.educationalsystem.document;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Data
@Builder
@Document
@EqualsAndHashCode(callSuper = true)
public class Puzzle extends Subtype {
    private Set<String> words;
}
