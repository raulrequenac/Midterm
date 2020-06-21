package com.ironhack.midterm.exceptions;

public class IllegalMinimumBalanceException extends RuntimeException {
    public IllegalMinimumBalanceException() {
    }

    public IllegalMinimumBalanceException(String message) {
        super(message);
    }
}
