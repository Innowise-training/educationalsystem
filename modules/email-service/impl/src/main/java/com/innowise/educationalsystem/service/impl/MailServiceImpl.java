package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.dto.MailRequest;
import com.innowise.educationalsystem.dto.MailResponse;
import com.innowise.educationalsystem.entity.Mail;
import com.innowise.educationalsystem.entity.enums.CreationStatus;
import com.innowise.educationalsystem.repository.MailRepository;
import com.innowise.educationalsystem.service.MailService;
import com.innowise.educationalsystem.service.VersionService;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final MailRepository mailRepository;
    private final VersionService versionService;

    @Override
    @Transactional
    public MailResponse createMail(MailRequest mailRequest) {
        //TODO: Some html template creation

        Mail savedMail = mailRepository.save(Mail.builder()
            .mailType(mailRequest.getMailType())
            .creationStatus(CreationStatus.SUCCESSFUL)
            .destinationEmail(mailRequest.getDestinationEmail())
            .build());

        versionService.createInitVersion(mailRequest.getPayload(), savedMail);

        return MailResponse.builder()
            .mailId(savedMail.getId())
            .creationStatus(CreationStatus.SUCCESSFUL)
            .build();
    }

    @Override
    public Mail find(String mailId) {
        return mailRepository.findById(mailId).orElseThrow(EntityNotFoundException::new);
    }
}
