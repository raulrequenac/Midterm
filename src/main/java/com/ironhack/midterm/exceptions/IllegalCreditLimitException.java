package com.ironhack.midterm.exceptions;

import com.ironhack.midterm.MidtermApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IllegalCreditLimitException extends RuntimeException {
    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public IllegalCreditLimitException() {
        super("The credit limit must be higher than 100 but not higher than 100000");
        LOGGER.warn("[ERROR] - IllegalCreditLimitException");
    }
}
