package com.innowise.educationalsystem.service;

import com.innowise.educationalsystem.dto.JwtDto;
import com.innowise.educationalsystem.dto.LoginRequestDto;
import com.innowise.educationalsystem.dto.UserSignUpRequestDto;
import com.innowise.educationalsystem.entity.User;

public interface AuthService {
    JwtDto loginUser(LoginRequestDto request, Long subscriptionId);

    User signUp(UserSignUpRequestDto userSignUpRequestDto);
}
