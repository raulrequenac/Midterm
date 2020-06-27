package com.ironhack.midterm.exceptions;

import com.ironhack.midterm.MidtermApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NameNotFoundException extends RuntimeException {
    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public NameNotFoundException() {
        super("The account does not have an owner whit the provided name");
        LOGGER.warn("[ERROR] - NameNotFoundException");
    }
}
