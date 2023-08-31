package com.innowise.educationalsystem.service.impl;

import com.innowise.educationalsystem.dto.UserHavePermissionsRequestDto;
import com.innowise.educationalsystem.dto.UserHavePermissionsResponseDto;
import com.innowise.educationalsystem.entity.Permission;
import com.innowise.educationalsystem.entity.Role;
import com.innowise.educationalsystem.entity.User;
import com.innowise.educationalsystem.repository.UserRepository;
import com.innowise.educationalsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    /**
     * @param userHavePermissionsRequestDto {@link UserHavePermissionsRequestDto}
     * @return List of {@link User} NOT have a requested roles.
     */
    @Override
    @Transactional(readOnly = true)
    // TODO: optimize
    public List<UserHavePermissionsResponseDto> verifyUserAuthorities(UserHavePermissionsRequestDto userHavePermissionsRequestDto) {
        return userHavePermissionsRequestDto.getUserIdList().stream()
                .map(userId -> userRepository.findUserWithRolesAndPermissionsById(userId)
                        .orElseThrow(EntityNotFoundException::new))
                .map(user -> {
                    boolean haveRequestedPermissions = userHavePermissionsRequestDto.getAuthorityList().stream()
                            .allMatch(requestedAuthority -> user.getRoles().stream()
                                    .map(Role::getPermissions)
                                    .flatMap(authorities -> authorities.stream()
                                            .map(Permission::getDescription))
                                    .anyMatch(requestedAuthority::equals)
                            );
                    return UserHavePermissionsResponseDto.builder()
                            .userId(user.getId())
                            .email(user.getEmail())
                            .havingRequestedPermissions(haveRequestedPermissions)
                            .build();
                }).collect(Collectors.toList());
    }
}
