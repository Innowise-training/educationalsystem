package com.innowise.educationalsystem.service;

import com.innowise.educationalsystem.dto.JwtDto;
import com.innowise.educationalsystem.dto.LoginRequestDto;
import com.innowise.educationalsystem.exception.NoSuchUserException;
import com.innowise.educationalsystem.exception.UserWrongCredentials;

public interface AuthService {
    JwtDto loginUser(LoginRequestDto request, Long subscriptionId);
}
