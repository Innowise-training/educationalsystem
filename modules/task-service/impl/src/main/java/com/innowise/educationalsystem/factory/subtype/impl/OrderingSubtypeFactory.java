package com.innowise.educationalsystem.factory.subtype.impl;

import com.innowise.educationalsystem.document.Subtype;
import com.innowise.educationalsystem.document.subtype.ordering.impl.PuzzleSubtype;
import com.innowise.educationalsystem.document.subtype.ordering.impl.SemiwritingSubtype;
import com.innowise.educationalsystem.dto.temp.SubtypeDto;
import com.innowise.educationalsystem.exception.SubtypeCreationException;
import com.innowise.educationalsystem.factory.subtype.SubtypeFactory;
import org.springframework.stereotype.Component;

@Component("orderingSubtypeFactory")
public class OrderingSubtypeFactory implements SubtypeFactory {
    @Override
    public Subtype<?> provideSubtype(SubtypeDto subtypeDto) {

        switch (subtypeDto.getName().toLowerCase()) {
            case "semiwriting": return new SemiwritingSubtype(subtypeDto);
            case "puzzle": return new PuzzleSubtype(subtypeDto);
            default: throw new SubtypeCreationException("Subtype with name \"" + subtypeDto.getName() + "\" does not supported for ordering type");
        }
    }
}
