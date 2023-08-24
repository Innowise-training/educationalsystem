package com.innowise.educationalsystem.client;

import com.innowise.educationalsystem.entity.Invite;

public interface NotificationClient {
    void sendInvite(Invite invite);
}
