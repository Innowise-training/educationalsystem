package com.innowise.educationalsystem.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.innowise.educationalsystem.config.SecurityProperties;
import com.innowise.educationalsystem.dto.JwtDto;
import com.innowise.educationalsystem.dto.LoginRequestDto;
import com.innowise.educationalsystem.dto.UserSignUpRequestDto;
import com.innowise.educationalsystem.entity.Invite;
import com.innowise.educationalsystem.entity.Permission;
import com.innowise.educationalsystem.entity.Role;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private static final String TOKEN_SUBSCRIPTION = "subscription_id";
    private static final String TOKEN_AUTHORITIES = "authorities";
    private static final String USER_ID_CLAIM = "user_id";

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final JwtMapper jwtMapper;

    private final PasswordEncoder passwordEncoder;

    private final SecurityProperties properties;

    private final InviteService inviteService;

    @Override
    public JwtDto loginUser(LoginRequestDto request, Long subscriptionId) {
        User foundUser = findUserByCredentials(request);
        authenticateUser(request, foundUser);
        return jwtMapper.rawTokenToDto(generateToken(foundUser, subscriptionId));
    }

    @Override
    @Transactional
    public User signUp(UserSignUpRequestDto userSignUpRequestDto) {
        Invite invite = inviteService.getForSignUp(userSignUpRequestDto.getInviteId());
        checkIfUserAlreadyExists(userSignUpRequestDto);
        User user = invite.getUser();
        userMapper.updateUserByDto(userSignUpRequestDto, user); // update user according to dto
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = userRepository.save(user);
        inviteService.close(invite);
        log.info("Created user with id {}", user.getId());
        return user;
    }

    private User findUserByCredentials(LoginRequestDto request) {
        return userRepository.findUserByUsernameWithRolesAndPermissions(request.getUsername())
                .orElseThrow(() -> new NoSuchUserException("Bad credentials"));
    }

    private void authenticateUser(LoginRequestDto request, User foundUser) {
        if (!passwordEncoder.matches(request.getPassword(), foundUser.getPassword())) {
            throw new UserWrongCredentials("Wrong credentials");
        }
    }

    private String generateToken(User foundUser, Long subscriptionId) {
        long now = System.currentTimeMillis();
        List<String> userPermissions = foundUser.getRoles().stream()
                .map(Role::getPermissions)
                .flatMap(authorities -> authorities.stream()
                        .map(Permission::getDescription))
                .collect(Collectors.toList());

        return JWT.create()
                .withSubject(foundUser.getUsername())
                .withClaim(TOKEN_SUBSCRIPTION, subscriptionId)
                .withClaim(TOKEN_AUTHORITIES, userPermissions)
                .withClaim(USER_ID_CLAIM, foundUser.getId())
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