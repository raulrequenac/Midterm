package com.ironhack.midterm.exceptions;

public class FraudDetectedException extends RuntimeException {
    public FraudDetectedException() {
    }

    public FraudDetectedException(String message) {
        super(message);
    }
}
