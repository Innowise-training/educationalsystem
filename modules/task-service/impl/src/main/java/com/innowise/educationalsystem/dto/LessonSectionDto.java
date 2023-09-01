package com.innowise.educationalsystem.dto;

import com.innowise.educationalsystem.entity.Lesson;
import lombok.Data;

@Data
public class LessonSectionDto {
    private String id;

    private String section;

    private String comment;

    private Lesson lesson;
}
