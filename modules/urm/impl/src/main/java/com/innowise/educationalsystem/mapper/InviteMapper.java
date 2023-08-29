package com.innowise.educationalsystem.mapper;

import com.innowise.educationalsystem.dto.InviteNotificationDto;
import com.innowise.educationalsystem.dto.InviteRequestDto;
import com.innowise.educationalsystem.dto.ValidatedInviteResponseDto;
import com.innowise.educationalsystem.entity.Invite;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InviteMapper {
    Invite requestDtoToEntity(InviteRequestDto inviteRequestDto);

    InviteNotificationDto entityToNotificationDto(Invite invite);

    ValidatedInviteResponseDto entityToValidateResponseDto(Invite invite);
}
