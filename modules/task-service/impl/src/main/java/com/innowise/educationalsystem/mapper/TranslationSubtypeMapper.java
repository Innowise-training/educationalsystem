package com.innowise.educationalsystem.mapper;

import com.innowise.educationalsystem.document.Subtype;

import com.innowise.educationalsystem.document.subtype.translation.impl.PuzzleSubtype;
import com.innowise.educationalsystem.document.subtype.translation.impl.WritingSubtype;
import com.innowise.educationalsystem.dto.subtype.ordering.impl.SemiwritingSubtypeDto;
import com.innowise.educationalsystem.dto.subtype.translation.TranslationSubtype;
import com.innowise.educationalsystem.dto.subtype.translation.impl.PuzzleSubtypeDto;
import com.innowise.educationalsystem.dto.subtype.translation.impl.WritingSubtypeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TranslationSubtypeMapper {

    PuzzleSubtype map(PuzzleSubtypeDto subtypeDto);

    WritingSubtype map(WritingSubtypeDto writingSubtypeDto);

}
