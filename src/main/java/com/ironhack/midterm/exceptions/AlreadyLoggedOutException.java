package com.ironhack.midterm.exceptions;

public class AlreadyLoggedOutException extends RuntimeException {
    public AlreadyLoggedOutException() {
    }

    public AlreadyLoggedOutException(String message) {
        super(message);
    }
}
