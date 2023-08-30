package com.innowise.educationalsystem.unit.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUnitDto {
    @NotBlank(message = "Name may not be blank")
    private String name;

    @NotBlank(message = "Description may not be blank")
    private String description;

    private String courseId;
}
