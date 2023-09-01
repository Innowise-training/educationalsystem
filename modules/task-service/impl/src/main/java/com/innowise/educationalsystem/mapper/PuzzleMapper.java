package com.innowise.educationalsystem.mapper;

import com.innowise.educationalsystem.document.Puzzle;
import com.innowise.educationalsystem.dto.PuzzleDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PuzzleMapper {
    PuzzleDto toDto(Puzzle puzzle);

    Puzzle toPuzzle(PuzzleDto puzzleDto);
}
