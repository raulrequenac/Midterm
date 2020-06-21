package com.ironhack.midterm.exceptions;

public class IllegalInterestRateException extends RuntimeException {
    public IllegalInterestRateException() {
    }

    public IllegalInterestRateException(String message) {
        super(message);
    }
}
