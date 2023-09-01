package com.innowise.educationalsystem.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.validation.ConstraintViolation;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private String logId;

    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private String message;

    private String detailedMessage;

    private List<ApiSubError> subErrors;

    public ApiError() {
        timestamp = LocalDateTime.now();
    }

    public ApiError(HttpStatus status) {
        this();
        this.status = status;
    }

    public ApiError(String logId, HttpStatus status, Throwable throwable) {
        this(status);
        this.logId = logId;
        this.message = "Unexpected error";
        this.detailedMessage = throwable.getLocalizedMessage();
    }

    public ApiError(HttpStatus status, String message, Throwable throwable) {
        this(status);
        this.message = message;
        this.detailedMessage = throwable.getLocalizedMessage();
    }

    public void addSubError(ApiSubError subError) {
        if (subErrors == null) {
            subErrors = new ArrayList<>();
        }
        subErrors.add(subError);
    }

    public void addValidationFieldErrors(List<FieldError> fieldErrors) {
        for (FieldError fieldError : fieldErrors) {
            ApiSubError subError = new ApiValidationError(
                    fieldError.getObjectName(),
                    fieldError.getField(),
                    fieldError.getRejectedValue(),
                    fieldError.getDefaultMessage());

            addSubError(subError);
        }
    }

    public void addValidationGlobalErrors(List<ObjectError> objectErrors) {
        for (ObjectError objectError : objectErrors) {
            ApiSubError subError = new ApiValidationError(
                    objectError.getObjectName(),
                    objectError.getDefaultMessage());

            addSubError(subError);
        }
    }

    public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
        for (ConstraintViolation<?> constraintViolation : constraintViolations) {
            ApiSubError subError = new ApiValidationError(
                    constraintViolation.getRootBeanClass().getSimpleName(),
                    ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().asString(),
                    constraintViolation.getInvalidValue(),
                    constraintViolation.getMessage());

            addSubError(subError);
        }
    }
}
