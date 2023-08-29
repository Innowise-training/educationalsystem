package com.innowise.educationalsystem.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FieldMatchValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldMatch {
    String field();

    String confirmField();

    String message() default "Fields must be matched!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
