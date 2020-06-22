package com.ironhack.midterm.exceptions;

public class AlreadyLoggedInException extends RuntimeException {
    public AlreadyLoggedInException() {
    }

    public AlreadyLoggedInException(String message) {
        super(message);
    }
}
