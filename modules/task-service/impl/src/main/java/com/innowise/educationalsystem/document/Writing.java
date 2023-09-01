package com.innowise.educationalsystem.document;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document
@EqualsAndHashCode(callSuper = true)
public class Writing extends Subtype {
}
