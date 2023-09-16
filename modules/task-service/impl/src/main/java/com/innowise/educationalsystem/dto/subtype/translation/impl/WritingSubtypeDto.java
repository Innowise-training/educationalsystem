package com.innowise.educationalsystem.dto.subtype.translation.impl;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.innowise.educationalsystem.dto.subtype.translation.TranslationSubtype;
import lombok.Data;

@Data
@JsonTypeName("Writing")
public class WritingSubtypeDto extends TranslationSubtype<Void> {
}
