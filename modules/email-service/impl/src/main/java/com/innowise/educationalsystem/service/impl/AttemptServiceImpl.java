package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.dto.AttemptRequest;
import com.innowise.educationalsystem.dto.AttemptResponse;
import com.innowise.educationalsystem.entity.Attempt;
import com.innowise.educationalsystem.entity.Mail;
import com.innowise.educationalsystem.entity.Version;
import com.innowise.educationalsystem.entity.enums.AttemptStatus;
import com.innowise.educationalsystem.repository.AttemptRepository;
import com.innowise.educationalsystem.service.AttemptService;
import com.innowise.educationalsystem.service.MailService;
import com.innowise.educationalsystem.service.SenderService;
import com.innowise.educationalsystem.service.VersionService;
import java.util.Comparator;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttemptServiceImpl implements AttemptService {
    private final AttemptRepository attemptRepository;
    private final VersionService versionService;
    private final SenderService senderService;
    private final MailService mailService;

    @Override
    @Transactional
    public AttemptResponse mailAttempt(AttemptRequest attemptRequest) {
        Mail mail = mailService.find(attemptRequest.getMailId());

        attemptRequest.getPayload()
            .ifPresent(p -> mail.getVersions().add(versionService.createVersion(p, mail)));

        Version actualVersion = mail.getVersions().stream()
            .max(Comparator.comparingInt(Version::getVersionNumber))
            .orElseThrow(EntityNotFoundException::new);

        senderService.sendMail(mail.getDestinationEmail(), actualVersion);

        Attempt savedAttempt = attemptRepository.save(Attempt.builder()
            .attemptStatus(AttemptStatus.SUCCESSFUL)
            .version(actualVersion)
            .build());

        return AttemptResponse.builder()
            .attemptId(savedAttempt.getId())
            .mailId(mail.getId())
            .version(actualVersion.getVersionNumber())
            .attemptStatus(savedAttempt.getAttemptStatus())
            .build();
    }
}
