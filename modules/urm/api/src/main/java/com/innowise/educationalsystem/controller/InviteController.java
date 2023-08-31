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
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('INVITE_USER')")
    public ResponseEntity<?> create(
            @RequestBody @Valid InviteRequestDto inviteRequestDto,
            @PathVariable("subscriptionId") Long subscriptionId) {
        inviteService.create(inviteRequestDto, subscriptionId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{inviteId}")
    @PreAuthorize("hasAuthority('INVITE_USER')")
    public ResponseEntity<ValidatedInviteResponseDto> validateInvite(@PathVariable("inviteId") String id) {
        Invite invite = inviteService.validateInvite(id);
        ValidatedInviteResponseDto validateInviteResponseDto = inviteMapper.entityToValidateResponseDto(invite);
        return ResponseEntity.ok(validateInviteResponseDto);
    }
}

