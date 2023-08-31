package com.innowise.educationalsystem.service;

import com.innowise.educationalsystem.client.NotificationClient;
import com.innowise.educationalsystem.client.mock.MockNotificationClient;
import com.innowise.educationalsystem.dto.InviteRequestDto;
import com.innowise.educationalsystem.entity.Invite;
import com.innowise.educationalsystem.entity.InviteStatus;
import com.innowise.educationalsystem.entity.Role;
import com.innowise.educationalsystem.exception.ClosedInviteException;
import com.innowise.educationalsystem.repository.InviteRepository;
import com.innowise.educationalsystem.repository.RoleRepository;
import com.innowise.educationalsystem.service.impl.InviteServiceImpl;
import org.apache.commons.compress.utils.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static java.util.Collections.emptySet;
import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;

class InviteServiceImplTest {
    private InviteServiceImpl inviteService;

    private NotificationClient notificationClient;
    private InviteRepository inviteRepository;
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        notificationClient = Mockito.mock(MockNotificationClient.class);
        inviteRepository = Mockito.mock(InviteRepository.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        inviteService = new InviteServiceImpl(notificationClient, inviteRepository, roleRepository);
        ReflectionTestUtils.setField(inviteService, "validationExpirationTime", Duration.ofHours(24));
        ReflectionTestUtils.setField(inviteService, "registrationExpirationTime", Duration.ofHours(1));
    }

    @Test
    public void create() {
        long subscriptionId = 1L;
        InviteRequestDto requestDto = new InviteRequestDto();
        requestDto.setEmail("email@gmail.com");
        requestDto.setRoleIds(Sets.newHashSet("1"));

        Role role = new Role("1", "TUTOR", true, emptySet());

        Invite savedInvite = createDefaultInvite();
        Mockito.when(roleRepository.findAllById(requestDto.getRoleIds()))
                .thenReturn(singletonList(role));
        Mockito.when(inviteRepository.save(any(Invite.class)))
                .thenReturn(savedInvite);

        inviteService.create(requestDto, subscriptionId);

        ArgumentCaptor<Invite> inviteCaptor = ArgumentCaptor.forClass(Invite.class);
        Mockito.verify(inviteRepository).save(inviteCaptor.capture());

        Assertions.assertEquals(Sets.newHashSet(role), inviteCaptor.getValue().getRoles());
        Assertions.assertEquals(InviteStatus.NEW, inviteCaptor.getValue().getStatus());
        Assertions.assertNotNull(inviteCaptor.getValue().getValidationExpiredAt());
        Mockito.verify(notificationClient).sendInvite(savedInvite);
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
        invite.setRoles(Sets.newHashSet(new Role("1", "TUTOR", true, emptySet())));
        invite.setSubscriptionId(1L);
        invite.setCreatedAt(LocalDateTime.now());
        invite.setValidationExpiredAt(LocalDateTime.now().plusSeconds(10));

        return invite;
    }
}
