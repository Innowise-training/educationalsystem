package com.innowise.educationalsystem.exception;

public class NoSuchUserException extends RuntimeException {
    public NoSuchUserException() {
    }

    public NoSuchUserException(String message) {
        super(message);
    }
}
