package com.ironhack.midterm.exceptions;

public class ForbiddenAccessException extends RuntimeException {
    public ForbiddenAccessException() {
    }

    public ForbiddenAccessException(String message) {
        super(message);
    }
}
