package com.ironhack.midterm.exceptions;

import com.ironhack.midterm.MidtermApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ForbiddenAccessException extends RuntimeException {
    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public ForbiddenAccessException() {
        super("The user does not have the permissions to access this account");
        LOGGER.warn("[ERROR] - ForbiddenAccessException");
    }
}
