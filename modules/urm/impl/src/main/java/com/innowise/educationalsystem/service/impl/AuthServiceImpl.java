package com.innowise.educationalsystem.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.innowise.educationalsystem.config.SecurityProperties;
import com.innowise.educationalsystem.dto.JwtDto;
import com.innowise.educationalsystem.dto.LoginRequestDto;
import com.innowise.educationalsystem.dto.UserResponseDto;
import com.innowise.educationalsystem.dto.UserSignUpRequestDto;
import com.innowise.educationalsystem.entity.Invite;
import com.innowise.educationalsystem.entity.User;
import com.innowise.educationalsystem.exception.NoSuchUserException;
import com.innowise.educationalsystem.exception.UserAlreadyExistsException;
import com.innowise.educationalsystem.exception.UserWrongCredentials;
import com.innowise.educationalsystem.mapper.JwtMapper;
import com.innowise.educationalsystem.mapper.UserMapper;
import com.innowise.educationalsystem.repository.UserRepository;
import com.innowise.educationalsystem.service.AuthService;
import com.innowise.educationalsystem.service.InviteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final String TOKEN_SUBSCRIPTION = "subscription_id";

    private static final String TOKEN_AUTHORITIES = "authorities";

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final JwtMapper jwtMapper;

    private final PasswordEncoder passwordEncoder;

    private final SecurityProperties properties;

    private final InviteService inviteService;

    @Override
    public JwtDto loginUser(LoginRequestDto request, Long subscriptionId) {
        UserResponseDto response = findUserByCredentials(request);
        authenticateUser(request, response);
        return jwtMapper.rawTokenToDto(generateToken(response, subscriptionId));
    }

    @Override
    public User signUp(UserSignUpRequestDto userSignUpRequestDto) {
        Invite invite = inviteService.getForSignUp(userSignUpRequestDto.getInviteId());
        checkIfUserAlreadyExists(userSignUpRequestDto);

        User newUser = userMapper.signUpDtoToEntity(userSignUpRequestDto);
        newUser.setPassword(passwordEncoder.encode(userSignUpRequestDto.getPassword()));
        newUser.setSubscriptionId(invite.getSubscriptionId());
        newUser.setEmail(invite.getEmail());
        newUser.setRoles(invite.getRoles());

        newUser = userRepository.save(newUser);
        inviteService.close(invite);
        log.info("Created user with id {}", newUser.getId());
        return newUser;
    }

    private UserResponseDto findUserByCredentials(LoginRequestDto request) {
        Optional<User> userByUsername = userRepository.findUserByUsername(request.getUsername());
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
                .withClaim(TOKEN_SUBSCRIPTION, subscriptionId)
                .withClaim(TOKEN_AUTHORITIES, response.getRoles())
                .withIssuedAt(new Date(now))
                .withExpiresAt(new Date(now + properties.getTokenExpire().toMillis()))
                .sign(Algorithm.HMAC256(properties.getSecretKey()));
    }

    private void checkIfUserAlreadyExists(UserSignUpRequestDto userSignUpRequestDto) {
        userRepository.findUserByUsername(userSignUpRequestDto.getUsername())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException(String.format(
                            "User with username %s already exists", user.getUsername()));
                });
    }
}