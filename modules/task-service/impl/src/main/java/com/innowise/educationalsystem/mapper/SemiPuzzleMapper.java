package com.innowise.educationalsystem.mapper;

import com.innowise.educationalsystem.document.SemiPuzzle;
import com.innowise.educationalsystem.dto.SemiPuzzleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SemiPuzzleMapper {
    SemiPuzzleDto toDto(SemiPuzzle semiPuzzle);

    SemiPuzzle toSemiPuzzle(SemiPuzzleDto semiPuzzleDto);
}
