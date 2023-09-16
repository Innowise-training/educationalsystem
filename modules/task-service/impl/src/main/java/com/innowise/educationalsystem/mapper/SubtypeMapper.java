package com.innowise.educationalsystem.mapper;

import com.innowise.educationalsystem.document.Subtype;
import com.innowise.educationalsystem.dto.NewSubtypeDto;
import com.innowise.educationalsystem.dto.subtype.ordering.impl.OrderingPuzzleSubtypeDto;
import com.innowise.educationalsystem.dto.subtype.ordering.impl.SemiwritingSubtypeDto;
import com.innowise.educationalsystem.dto.subtype.translation.impl.PuzzleSubtypeDto;
import com.innowise.educationalsystem.dto.subtype.translation.impl.WritingSubtypeDto;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class SubtypeMapper {

    @Autowired OrderingSubtypeMapper orderingSubtypeMapper;

    @Autowired TranslationSubtypeMapper translationSubtypeMapper;

    Subtype<?> map(NewSubtypeDto<?> subtypeDto) {
        if (subtypeDto instanceof PuzzleSubtypeDto) {
            return translationSubtypeMapper.map((PuzzleSubtypeDto) subtypeDto);
        } else if (subtypeDto instanceof WritingSubtypeDto) {
            return translationSubtypeMapper.map((WritingSubtypeDto) subtypeDto);
        } else if (subtypeDto instanceof OrderingPuzzleSubtypeDto) {
            return orderingSubtypeMapper.map((OrderingPuzzleSubtypeDto) subtypeDto);
        } else if (subtypeDto instanceof SemiwritingSubtypeDto) {
            return orderingSubtypeMapper.map((SemiwritingSubtypeDto) subtypeDto);
        }

        throw new RuntimeException("Such subtype was not found");
    }
}
