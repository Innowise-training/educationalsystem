package com.innowise.educationalsystem.unit.dto;

import com.innowise.educationalsystem.unit.validation.IdExists;
import lombok.Data;

@Data
public class UpdateUnitDto {
    @IdExists
    private String id;

    private String name;

    private String description;

    private String courseId;
}
