package com.innowise.educationalsystem.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
public class InviteRequestDto {
    @Email
    @NotBlank
    private String email;

    @NotNull
    private Set<String> roleIds;
}
