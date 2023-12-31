package com.innowise.educationalsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private String id;

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    private List<RoleResponseDto> roles;
}
