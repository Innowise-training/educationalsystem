package com.innowise.educationalsystem.service;

import com.innowise.educationalsystem.dto.UserHavePermissionsDto;
import com.innowise.educationalsystem.dto.UserHavePermissionsRequestDto;

import java.util.List;

public interface UserService {
    List<UserHavePermissionsDto> verifyUserAuthorities(UserHavePermissionsRequestDto userHavePermissionsRequestDto);

}
