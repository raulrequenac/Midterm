package com.ironhack.midterm.exceptions;

public class NameNotFoundException extends RuntimeException {
    public NameNotFoundException() {
    }

    public NameNotFoundException(String message) {
        super(message);
    }
}
