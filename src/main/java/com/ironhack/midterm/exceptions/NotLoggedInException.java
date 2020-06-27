package com.ironhack.midterm.exceptions;

import com.ironhack.midterm.MidtermApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NotLoggedInException extends RuntimeException {
    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public NotLoggedInException() {
        super("The user is not logged in");
        LOGGER.warn("[ERROR] - NotLoggedInException");
    }
}
