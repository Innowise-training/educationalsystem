package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.entity.Mail;
import com.innowise.educationalsystem.entity.Version;
import com.innowise.educationalsystem.repository.VersionRepository;
import com.innowise.educationalsystem.service.VersionService;
import java.util.Comparator;
import java.util.Map;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VersionServiceImpl implements VersionService {
    private final VersionRepository versionRepository;

    @Override
    public Version createVersion(Map<String, Object> newPayload, Mail mail) {

        Version previousVersion = mail.getVersions().stream()
            .max(Comparator.comparingInt(Version::getVersionNumber))
            .orElseThrow(EntityNotFoundException::new);

        return versionRepository.save(Version.builder()
            .versionNumber(previousVersion.getVersionNumber() + 1)
            .previousVersion(previousVersion)
            .mail(mail)
            .payload(newPayload)
            .build());
    }

    @Override
    public Version createInitVersion(Map<String, Object> newPayload, Mail mail) {
        return versionRepository.save(Version.builder()
            .versionNumber(1)
            .previousVersion(null)
            .mail(mail)
            .payload(newPayload)
            .build());
    }
}
