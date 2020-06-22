package com.ironhack.midterm.exceptions;

public class NotEnoughBalanceException extends RuntimeException {
    public NotEnoughBalanceException() {
    }

    public NotEnoughBalanceException(String message) {
        super(message);
    }
}
