package com.innowise.educationalsystem.dto;

import com.innowise.educationalsystem.validation.FieldMatch;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@FieldMatch(field = "password",
            confirmField = "confirmPassword",
            message = "Passwords must be matched!")
public class UserSignUpRequestDto {
    @NotNull
    private String inviteId;

    @NotBlank
    private String username;

    @NotBlank
    @Length(min = 4, message = "Password must be at least 4 characters long")
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    private String firstname;

    @NotBlank
    private String lastname;
}
