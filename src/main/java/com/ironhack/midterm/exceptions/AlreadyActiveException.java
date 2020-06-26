package com.ironhack.midterm.exceptions;

public class AlreadyActiveException extends RuntimeException {
    public AlreadyActiveException() {
    }

    public AlreadyActiveException(String message) {
        super(message);
    }
}
