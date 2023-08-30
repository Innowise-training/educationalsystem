package com.innowise.educationalsystem.unit.validation.validator;

import com.innowise.educationalsystem.unit.repository.UnitRepository;
import com.innowise.educationalsystem.unit.validation.IdExists;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class IdExistsValidator implements ConstraintValidator<IdExists, String> {
    @Autowired
    private UnitRepository unitRepository;

    @Override
    public void initialize(IdExists constraintAnnotation) {
    }

    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        return unitRepository.existsById(id);
    }
}
