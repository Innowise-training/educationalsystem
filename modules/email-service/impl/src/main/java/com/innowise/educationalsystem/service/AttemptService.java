package com.innowise.educationalsystem.service;

import com.innowise.educationalsystem.dto.AttemptRequest;
import com.innowise.educationalsystem.dto.AttemptResponse;

public interface AttemptService {
    AttemptResponse mailAttempt(AttemptRequest attemptRequest);
}
