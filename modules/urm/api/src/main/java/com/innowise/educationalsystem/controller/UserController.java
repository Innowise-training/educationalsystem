package com.innowise.educationalsystem.controller;

import com.innowise.educationalsystem.dto.UserHavePermissionsResponseDto;
import com.innowise.educationalsystem.dto.UserHavePermissionsRequestDto;
import com.innowise.educationalsystem.dto.UserResponseDto;
import com.innowise.educationalsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;

    /**
     * @param userHavePermissionsRequestDto - include List<String> - userIdList, List<String> - permissions me check
     * @return List of {@link UserResponseDto} that NOT have a requestedRoles
     */
    @PostMapping("have-permissions")
    public List<UserHavePermissionsResponseDto> verifyRequestedAuthorities(
            @RequestBody @Valid UserHavePermissionsRequestDto userHavePermissionsRequestDto) {
        return userService.verifyUserAuthorities(userHavePermissionsRequestDto);
    }
}
