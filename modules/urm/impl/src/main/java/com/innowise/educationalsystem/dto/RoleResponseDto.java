package com.innowise.educationalsystem.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponseDto {
    private String id;

    private String name;

    private List<PermissionResponseDto> permissions;
}
