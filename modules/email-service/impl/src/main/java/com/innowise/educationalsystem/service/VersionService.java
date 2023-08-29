package com.innowise.educationalsystem.service;

import com.innowise.educationalsystem.entity.Mail;
import com.innowise.educationalsystem.entity.Version;
import java.util.Map;

public interface VersionService {
    Version createVersion(Map<String, Object> newPayload, Mail mail);

    Version createInitVersion(Map<String, Object> newPayload, Mail mail);
}
