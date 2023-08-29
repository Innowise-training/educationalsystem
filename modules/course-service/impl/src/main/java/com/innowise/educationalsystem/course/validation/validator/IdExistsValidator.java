package com.innowise.educationalsystem.course.validation.validator;

import com.innowise.educationalsystem.course.repository.CourseRepository;
import com.innowise.educationalsystem.course.validation.IdExists;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

public class IdExistsValidator implements ConstraintValidator<IdExists, String> {
    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void initialize(IdExists constraintAnnotation) {
    }

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return courseRepository.existsById(id);
    }
}
