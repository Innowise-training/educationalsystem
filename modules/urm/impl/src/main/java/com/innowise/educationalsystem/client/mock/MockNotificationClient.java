package com.innowise.educationalsystem.client.mock;

import com.innowise.educationalsystem.client.NotificationClient;
import com.innowise.educationalsystem.entity.Invite;
import com.innowise.educationalsystem.mapper.InviteMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MockNotificationClient implements NotificationClient {
    private final InviteMapper inviteMapper;

    @Override
    public void sendInvite(Invite invite) {
        log.info("Sending {}", inviteMapper.entityToNotificationDto(invite));
    }
}

