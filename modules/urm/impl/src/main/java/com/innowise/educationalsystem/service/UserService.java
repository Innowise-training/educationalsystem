package com.innowise.educationalsystem.service;

import com.innowise.educationalsystem.dto.UserHavePermissionsResponseDto;
import com.innowise.educationalsystem.dto.UserHavePermissionsRequestDto;

import java.util.List;

public interface UserService {
    List<UserHavePermissionsResponseDto> verifyUserAuthorities(UserHavePermissionsRequestDto userHavePermissionsRequestDto);

}
