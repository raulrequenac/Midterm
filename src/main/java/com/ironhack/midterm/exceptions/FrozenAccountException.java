package com.ironhack.midterm.exceptions;

import com.ironhack.midterm.MidtermApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FrozenAccountException extends RuntimeException {
    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public FrozenAccountException() {
        super("The account is frozen");
        LOGGER.warn("[ERROR] - FrozenAccountException");
    }
}
