package com.innowise.educationalsystem.dto.subtype.translation;

import com.innowise.educationalsystem.dto.NewSubtypeDto;

public abstract class TranslationSubtype<ADD> extends NewSubtypeDto<ADD> {
    protected ADD additionalData;
}
