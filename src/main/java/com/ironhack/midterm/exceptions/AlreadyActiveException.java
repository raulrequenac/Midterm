package com.ironhack.midterm.exceptions;

import com.ironhack.midterm.MidtermApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AlreadyActiveException extends RuntimeException {
    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public AlreadyActiveException() {
        super("This account is already active");
        LOGGER.warn("[ERROR] - AlreadyActiveException");
    }
}
