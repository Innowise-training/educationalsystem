package com.innowise.educationalsystem.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class RoleRequestDto {
    @NotBlank
    private String id;

    @NotBlank
    private String name;

    @NotNull
    private Set<String> permissionIds;
}
