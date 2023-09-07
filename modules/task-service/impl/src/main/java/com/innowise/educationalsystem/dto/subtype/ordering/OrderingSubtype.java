package com.innowise.educationalsystem.dto.subtype.ordering;

import com.innowise.educationalsystem.dto.NewSubtypeDto;

public abstract class OrderingSubtype<ADD> extends NewSubtypeDto<ADD> {
    protected ADD additionalData;
}
