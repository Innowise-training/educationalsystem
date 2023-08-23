package com.innowise.educationalsystem.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.innowise.educationalsystem.config.SecurityProperties;
import com.innowise.educationalsystem.dto.JwtDto;
import com.innowise.educationalsystem.dto.LoginRequestDto;
import com.innowise.educationalsystem.dto.UserResponseDto;
import com.innowise.educationalsystem.entity.User;
import com.innowise.educationalsystem.exception.NoSuchUserException;
import com.innowise.educationalsystem.exception.UserWrongCredentials;
import com.innowise.educationalsystem.maper.JwtMapper;
import com.innowise.educationalsystem.maper.UserMapper;
import com.innowise.educationalsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final String TOKEN_SUBSCRIBTION = "subscription_id";

    private static final String TOKEN_AUTHORITIES = "authorities";

    private final UserRepository repository;

    private final UserMapper userMapper;

    private final JwtMapper jwtMapper;

    private final PasswordEncoder passwordEncoder;

    private final SecurityProperties properties;

    public JwtDto loginUser(LoginRequestDto request, Long subscriptionId) {
        UserResponseDto response = findUserByCredentials(request);
        authenticateUser(request, response);
        return jwtMapper.rawTokenToDto(generateToken(response, subscriptionId));
    }

    private UserResponseDto findUserByCredentials(LoginRequestDto request) {
        Optional<User> userByUsername = repository.findUserByUsername(request.getUsername());
        userByUsername.orElseThrow(() -> new NoSuchUserException("Nickname or password is wrong"));
        return userMapper.entityToDto(userByUsername.get());
    }

    private void authenticateUser(LoginRequestDto request, UserResponseDto foundUser) {
        if (!passwordEncoder.matches(request.getPassword(), foundUser.getPassword())) {
            throw new UserWrongCredentials("Wrong credentials");
        }
    }

    private String generateToken(UserResponseDto response, Long subscriptionId) {
        long now = System.currentTimeMillis();
        return JWT.create()
                .withSubject(response.getUsername())
                .withClaim(TOKEN_SUBSCRIBTION, subscriptionId)
                .withClaim(TOKEN_AUTHORITIES, response.getRoles())
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + properties.getTokenExpire().toMillis()))
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }
}
