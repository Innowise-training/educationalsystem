package com.innowise.educationalsystem.unit.validation;

import com.innowise.educationalsystem.unit.validation.validator.IdExistsValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IdExistsValidator.class)
@Documented
public @interface IdExists {
    String message() default "ID does not exist in the database";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
