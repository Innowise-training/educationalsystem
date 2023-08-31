package com.innowise.educationalsystem.exception;

public class NoSuchRoleException extends RuntimeException{
    public NoSuchRoleException() {
    }

    public NoSuchRoleException(String message) {
        super(message);
    }
}
