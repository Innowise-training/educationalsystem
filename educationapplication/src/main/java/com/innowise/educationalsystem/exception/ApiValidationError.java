package com.innowise.educationalsystem.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiValidationError extends ApiSubError {
    private String object;

    private String field;

    private Object rejectedValue;

    private String message;

    ApiValidationError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
