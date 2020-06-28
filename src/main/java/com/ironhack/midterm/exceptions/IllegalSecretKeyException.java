package com.ironhack.midterm.exceptions;

import com.ironhack.midterm.MidtermApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IllegalSecretKeyException extends RuntimeException {
    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public IllegalSecretKeyException() {
        super("The secret key must be greater than 0 but not greater than 9999");
        LOGGER.warn("[ERROR] - IllegalSecretKeyException");
    }
}
