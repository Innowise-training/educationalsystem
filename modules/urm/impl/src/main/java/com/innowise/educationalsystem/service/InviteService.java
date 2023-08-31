package com.innowise.educationalsystem.service;

import com.innowise.educationalsystem.dto.InviteRequestDto;
import com.innowise.educationalsystem.entity.Invite;

public interface InviteService {
    Invite create(InviteRequestDto inviteRequestDto, Long subscriptionId);

    Invite validateInvite(String id);

    Invite getForSignUp(String id);

    void close(Invite invite);
}
