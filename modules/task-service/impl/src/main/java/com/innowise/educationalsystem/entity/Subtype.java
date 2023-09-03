package com.innowise.educationalsystem.entity;

import com.innowise.educationalsystem.dto.SubtypeDto;
import lombok.Data;

@Data
public abstract class Subtype<ADD> {
    ADD additionalData;

    protected Subtype(SubtypeDto subtypeDto) {
        additionalData = (ADD) subtypeDto.getAdd();
    }
}


