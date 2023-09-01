package com.innowise.educationalsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserHavePermissionsResponseDto {
    private String userId;

    private String email;

    private boolean hasRequestedPermissions;
}
