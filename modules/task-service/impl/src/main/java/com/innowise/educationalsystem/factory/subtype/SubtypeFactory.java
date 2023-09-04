package com.innowise.educationalsystem.factory.subtype;

import com.innowise.educationalsystem.dto.SubtypeDto;
import com.innowise.educationalsystem.document.Subtype;

public interface SubtypeFactory {
    Subtype<?> provideSubtype(SubtypeDto subtypeDto);
}
