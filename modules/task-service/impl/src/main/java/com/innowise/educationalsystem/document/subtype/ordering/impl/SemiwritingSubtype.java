package com.innowise.educationalsystem.document.subtype.ordering.impl;

import com.innowise.educationalsystem.document.subtype.ordering.OrderingSubtype;
import com.innowise.educationalsystem.dto.SubtypeDto;
import org.springframework.data.annotation.TypeAlias;

import java.util.List;

@TypeAlias("Semiwriting")
public class SemiwritingSubtype extends OrderingSubtype<List<String>> {
    protected SemiwritingSubtype() {
        super();
    }

    public SemiwritingSubtype(SubtypeDto subtypeDto) {
        super(subtypeDto);
    }
}
