package com.innowise.educationalsystem.mapper;

import com.innowise.educationalsystem.document.Translation;
import com.innowise.educationalsystem.dto.TranslationDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TranslationMapper {
    TranslationDto toDto(Translation translation);

    Translation toTranslation(TranslationDto translationDto);
}
