package com.innowise.educationalsystem.course.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateCourseDto {
    @NotBlank(message = "Name may not be blank")
    private String name;

    @NotBlank(message = "Description may not be blank")
    private String description;
}
