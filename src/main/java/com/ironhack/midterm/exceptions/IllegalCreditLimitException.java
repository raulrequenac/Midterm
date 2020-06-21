package com.ironhack.midterm.exceptions;

public class IllegalCreditLimitException extends RuntimeException {
    public IllegalCreditLimitException() {
    }

    public IllegalCreditLimitException(String message) {
        super(message);
    }
}
