package com.innowise.educationalsystem.controller;

import com.innowise.educationalsystem.dto.InviteRequestDto;
import com.innowise.educationalsystem.dto.ValidatedInviteResponseDto;
import com.innowise.educationalsystem.entity.Invite;
import com.innowise.educationalsystem.mapper.InviteMapper;
import com.innowise.educationalsystem.service.InviteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth/invite")
public class InviteController {
    private final InviteService inviteService;
    private final InviteMapper inviteMapper;

    @PostMapping("/subscription/{subscriptionId}")
    public ResponseEntity<?> create(
            @RequestBody @Valid InviteRequestDto inviteRequestDto,
            @PathVariable("subscriptionId") Long subscriptionId) {
        Invite invite = inviteMapper.requestDtoToEntity(inviteRequestDto);
        inviteService.create(invite, subscriptionId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{inviteId}")
    public ResponseEntity<ValidatedInviteResponseDto> validateInvite(@PathVariable("inviteId") String id) {
        Invite invite = inviteService.validateInvite(id);
        ValidatedInviteResponseDto validateInviteResponseDto = inviteMapper.entityToValidateResponseDto(invite);
        return ResponseEntity.ok(validateInviteResponseDto);
    }
}

