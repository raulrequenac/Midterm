package com.ironhack.midterm.exceptions;

import com.ironhack.midterm.MidtermApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlreadyLoggedInException extends RuntimeException {
    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public AlreadyLoggedInException() {
        super("User is already logged in");
        LOGGER.warn("[ERROR] - AlreadyLoggedInException");
    }
}
