package com.innowise.educationalsystem.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleResponseDto {
    private String id;

    private String name;

    private boolean system;

    private List<PermissionResponseDto> permissions;
}
