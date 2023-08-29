package com.innowise.educationalsystem.controller;

import com.innowise.educationalsystem.dto.JwtDto;
import com.innowise.educationalsystem.dto.LoginRequestDto;
import com.innowise.educationalsystem.exception.NoSuchUserException;
import com.innowise.educationalsystem.exception.UserWrongCredentials;
import com.innowise.educationalsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login/subscription/{subscriptionId}")
    public ResponseEntity<JwtDto> login(
            @RequestBody @Valid LoginRequestDto request,
            @PathVariable Long subscriptionId) {
        return ResponseEntity.ok(authService.loginUser(request, subscriptionId));
    }
}
