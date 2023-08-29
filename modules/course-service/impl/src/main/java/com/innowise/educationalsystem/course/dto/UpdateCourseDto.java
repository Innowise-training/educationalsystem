package com.innowise.educationalsystem.course.dto;

import com.innowise.educationalsystem.course.validation.IdExists;
import lombok.Data;

@Data
public class UpdateCourseDto {
    @IdExists
    private String id;

    private String name;

    private String description;
}
