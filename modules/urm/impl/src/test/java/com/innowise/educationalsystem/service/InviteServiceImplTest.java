package com.innowise.educationalsystem.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.innowise.educationalsystem.client.NotificationClient;
import com.innowise.educationalsystem.client.mock.MockNotificationClient;
import com.innowise.educationalsystem.entity.Invite;
import com.innowise.educationalsystem.entity.InviteStatus;
import com.innowise.educationalsystem.exception.ClosedInviteException;
import com.innowise.educationalsystem.repository.InviteRepository;
import com.innowise.educationalsystem.service.impl.InviteServiceImpl;

import javax.persistence.EntityNotFoundException;

class InviteServiceImplTest {
    private InviteServiceImpl inviteService;

    private NotificationClient notificationClient;
    private InviteRepository inviteRepository;

    @BeforeEach
    void setUp() {
        notificationClient = Mockito.mock(MockNotificationClient.class);
        inviteRepository = Mockito.mock(InviteRepository.class);
        inviteService = new InviteServiceImpl(notificationClient, inviteRepository);
    }

    @Test
    public void create() {
        long subscriptionId = 1L;
        Invite invite = createDefaultInvite();
        invite.setId(null);
        invite.setValidationExpiredAt(null);

        Invite savedInvite = createDefaultInvite();
        Mockito.when(inviteRepository.save(invite))
            .thenReturn(savedInvite);

        Invite actualInvite = inviteService.create(invite, subscriptionId);

        Mockito.verify(notificationClient).sendInvite(savedInvite);
        Assertions.assertEquals(savedInvite.getId(), actualInvite.getId());
        Assertions.assertEquals(InviteStatus.NEW, actualInvite.getStatus());
        Assertions.assertNotNull(invite.getValidationExpiredAt());
    }

    @Test
    public void validateUnknownInvite() {
        String id = "UnknownId";
        Mockito.when(inviteRepository.findById(id))
            .thenReturn(Optional.empty());

        Assertions.assertThrows(EntityNotFoundException.class, () ->
            inviteService.validateInvite(id));
    }

    @Test
    public void validateExpiredInvite() {
        Invite invite = createDefaultInvite();
        invite.setValidationExpiredAt(LocalDateTime.now().minusSeconds(1));
        String id = invite.getId();

        Mockito.when(inviteRepository.findById(id))
            .thenReturn(Optional.of(invite));

        Assertions.assertThrows(ClosedInviteException.class, () ->
            inviteService.validateInvite(id));
    }

    @Test
    public void validateInvite() {
        Invite invite = createDefaultInvite();
        String id = invite.getId();

        Mockito.when(inviteRepository.findById(id))
            .thenReturn(Optional.of(invite));

        Invite actualInvite = inviteService.validateInvite(id);

        Assertions.assertNotNull(actualInvite);
        Assertions.assertNotNull(actualInvite.getRegistrationExpiredAt());
        Assertions.assertEquals(InviteStatus.VALIDATED, actualInvite.getStatus());
    }

    @Test
    public void validateAlreadyValidatedInvite() {
        Invite invite = createDefaultInvite();
        invite.setRegistrationExpiredAt(LocalDateTime.now().plusSeconds(10));
        invite.setStatus(InviteStatus.VALIDATED);
        String id = invite.getId();

        Mockito.when(inviteRepository.findById(id))
            .thenReturn(Optional.of(invite));

        Invite actualInvite = inviteService.validateInvite(id);

        Mockito.verify(inviteRepository, Mockito.never()).save(invite);
        Assertions.assertNotNull(actualInvite);
        Assertions.assertEquals(invite.getRegistrationExpiredAt(), actualInvite.getRegistrationExpiredAt());
        Assertions.assertEquals(InviteStatus.VALIDATED, actualInvite.getStatus());
    }

    @Test
    public void validateAlreadyValidatedInviteExpired() {
        Invite invite = createDefaultInvite();
        invite.setRegistrationExpiredAt(LocalDateTime.now().minusSeconds(10));
        invite.setStatus(InviteStatus.VALIDATED);
        String id = invite.getId();

        Mockito.when(inviteRepository.findById(id))
            .thenReturn(Optional.of(invite));

        Assertions.assertThrows(ClosedInviteException.class, () ->
            inviteService.validateInvite(id));
    }

    private Invite createDefaultInvite() {
        Invite invite = new Invite();
        invite.setId(UUID.randomUUID().toString());
        invite.setStatus(InviteStatus.NEW);
        invite.setEmail("email@gmail.com");
        invite.setRoles("TUTOR");
        invite.setSubscriptionId(1L);
        invite.setCreatedAt(LocalDateTime.now());
        invite.setValidationExpiredAt(LocalDateTime.now().plusSeconds(10));

        return invite;
    }
}
