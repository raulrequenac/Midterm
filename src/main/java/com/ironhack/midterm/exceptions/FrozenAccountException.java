package com.ironhack.midterm.exceptions;

public class FrozenAccountException extends RuntimeException {
    public FrozenAccountException() {
    }

    public FrozenAccountException(String message) {
        super(message);
    }
}
