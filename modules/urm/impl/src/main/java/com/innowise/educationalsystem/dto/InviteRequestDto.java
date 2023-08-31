package com.innowise.educationalsystem.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class InviteRequestDto {
    @Email
    @NotBlank
    private String email;

    // TODO: make ROLE list of Strings...

    @NotBlank
    private String roles;
}
