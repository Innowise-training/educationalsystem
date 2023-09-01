package com.innowise.educationalsystem.mapper;

import com.innowise.educationalsystem.dto.ValidatedInviteResponseDto;
import com.innowise.educationalsystem.entity.Invite;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InviteMapper {
    ValidatedInviteResponseDto entityToValidateResponseDto(Invite invite);
}
