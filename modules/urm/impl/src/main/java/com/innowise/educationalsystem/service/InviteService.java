package com.innowise.educationalsystem.service;

import com.innowise.educationalsystem.entity.Invite;

public interface InviteService {
    Invite create(Invite invite, Long subscriptionId);

    Invite validateInvite(String id);

    Invite getForSignUp(String id);

    void close(Invite invite);
}
