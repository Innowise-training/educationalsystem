package com.innowise.educationalsystem.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserHaveAuthoritiesRequestDto {
    @NotEmpty
    private List<String> userIdList;

    @NotEmpty
    private List<String> authorityList;
}
