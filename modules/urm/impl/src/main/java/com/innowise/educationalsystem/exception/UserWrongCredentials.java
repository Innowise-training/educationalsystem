package com.innowise.educationalsystem.exception;

public class UserWrongCredentials extends RuntimeException {
    public UserWrongCredentials() {
    }

    public UserWrongCredentials(String message) {
        super(message);
    }
}
