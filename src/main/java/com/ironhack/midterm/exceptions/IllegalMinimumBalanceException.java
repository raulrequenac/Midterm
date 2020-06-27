package com.ironhack.midterm.exceptions;

import com.ironhack.midterm.MidtermApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IllegalMinimumBalanceException extends RuntimeException {
    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public IllegalMinimumBalanceException() {
        super("The minimum balance must be greater than 100 but not greater than 1000");
        LOGGER.warn("[ERROR] - IllegalMinimumBalanceException");
    }
}
