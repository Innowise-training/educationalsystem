package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.client.NotificationClient;
import com.innowise.educationalsystem.entity.Invite;
import com.innowise.educationalsystem.entity.InviteStatus;
import com.innowise.educationalsystem.exception.ClosedInviteException;
import com.innowise.educationalsystem.exception.InviteNotValidatedException;
import com.innowise.educationalsystem.repository.InviteRepository;
import com.innowise.educationalsystem.service.InviteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class InviteServiceImpl implements InviteService {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private final NotificationClient notificationClient;
    private final InviteRepository inviteRepository;

    @Value("${app.urm.invite.expiration.validation}")
    private Duration validationExpirationTime;

    @Value("${app.urm.invite.expiration.registration}")
    private Duration registrationExpirationTime;

    @Override
    @Transactional
    public Invite create(Invite invite, Long subscriptionId) {
        rejectOldInvites(invite);

        invite.setSubscriptionId(subscriptionId);
        invite.setValidationExpiredAt(LocalDateTime.now().plus(validationExpirationTime));
        invite.setStatus(InviteStatus.NEW);
        invite = inviteRepository.save(invite);
        log.info("Created invite with id {}", invite.getId());

        notificationClient.sendInvite(invite);
        log.info("Sent invite with id {} to notification service", invite.getId());

        return invite;
    }

    @Override
    public Invite validateInvite(String id) {
        Invite invite = getById(id);
        checkIfInviteAvailable(invite);
        if (invite.getStatus() == InviteStatus.VALIDATED) {
            return invite;
        }

        invite.setRegistrationExpiredAt(LocalDateTime.now().plus(registrationExpirationTime));
        invite.setStatus(InviteStatus.VALIDATED);
        inviteRepository.save(invite);
        log.info("Validated invite with id {}", invite.getId());

        return invite;
    }

    @Override
    public Invite getForSignUp(String id) {
        Invite invite = getById(id);
        checkIfInviteAvailable(invite);
        if (invite.getStatus() != InviteStatus.VALIDATED) {
            throw new InviteNotValidatedException(String.format(
                "Invite with id %s is not validated. Invite status: %s",
                invite.getId(), invite.getStatus()));
        }

        log.info("Found invite with id {}", invite.getId());
        return invite;
    }

    @Override
    public void close(Invite invite) {
        invite.setStatus(InviteStatus.SUCCESS);
        inviteRepository.save(invite);
        log.info("Invite with id {} was closed with status {}",
            invite.getId(), invite.getStatus());
    }

    private Invite getById(String id) {
        return inviteRepository.findById(id).orElseThrow(() ->
            new EntityNotFoundException(String.format("Invite with id %s doesn't exist", id)));
    }

    private void rejectOldInvites(Invite newInvite) {
        inviteRepository.findAllByEmail(newInvite.getEmail())
            .forEach(invite -> {
                if (invite.getStatus() == InviteStatus.SUCCESS ||
                    invite.getStatus() == InviteStatus.REJECTED ||
                    invite.getStatus() == InviteStatus.EXPIRED) {
                    return;
                }

                invite.setStatus(InviteStatus.REJECTED);
                inviteRepository.save(invite);
                log.info("Invite with id {} was closed with status {}",
                    invite.getId(), invite.getStatus());
            });
    }

    private void checkIfInviteAvailable(Invite invite) {
        switch (invite.getStatus()) {
            case REJECTED:
            case SUCCESS:
            case EXPIRED:
                throw new ClosedInviteException(String.format(
                    "Invite with id %s is already closed. Invite status: %s",
                    invite.getId(), invite.getStatus()));
            case VALIDATED:
                checkIfExpired(invite, invite.getRegistrationExpiredAt());
                break;
            case NEW:
                checkIfExpired(invite, invite.getValidationExpiredAt());
                break;
        }
    }

    private void checkIfExpired(Invite invite, LocalDateTime expiredDate) {
        if (LocalDateTime.now().isAfter(expiredDate)) {
            invite.setStatus(InviteStatus.EXPIRED);
            inviteRepository.save(invite);

            throw new ClosedInviteException(String.format("Invite already expired at %s",
                expiredDate.format(FORMATTER)));
        }
    }
}
