package com.ironhack.midterm.exceptions;

import com.ironhack.midterm.MidtermApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlreadyLoggedOutException extends RuntimeException {
    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public AlreadyLoggedOutException() {
        super("User is already logged out");
        LOGGER.warn("[ERROR] - AlreadyLoggedOutException");
    }
}
