package com.innowise.educationalsystem.controller;

import com.innowise.educationalsystem.dto.JwtDto;
import com.innowise.educationalsystem.dto.LoginRequestDto;
import com.innowise.educationalsystem.dto.UserSignUpRequestDto;
import com.innowise.educationalsystem.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login/subscription/{subscriptionId}")
    public ResponseEntity<JwtDto> login(
            @RequestBody @Valid LoginRequestDto request,
            @PathVariable Long subscriptionId) {
        return ResponseEntity.ok(authService.loginUser(request, subscriptionId));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody @Valid UserSignUpRequestDto userSignUpRequestDto) {
        authService.signUp(userSignUpRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
