package com.innowise.educationalsystem.mapper;

import com.innowise.educationalsystem.document.subtype.ordering.impl.PuzzleSubtype;
import com.innowise.educationalsystem.document.subtype.ordering.impl.SemiwritingSubtype;
import com.innowise.educationalsystem.dto.subtype.ordering.impl.OrderingPuzzleSubtypeDto;
import com.innowise.educationalsystem.dto.subtype.ordering.impl.SemiwritingSubtypeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderingSubtypeMapper {
    PuzzleSubtype map(OrderingPuzzleSubtypeDto subtypeDto);

    SemiwritingSubtype map(SemiwritingSubtypeDto semiwritingSubtypeDto);
}
