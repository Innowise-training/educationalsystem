package com.innowise.educationalsystem.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class InviteRequestDto {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String roles;
}
