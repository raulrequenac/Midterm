package com.ironhack.midterm.exceptions;

import com.ironhack.midterm.MidtermApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IllegalInterestRateException extends RuntimeException {
    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public IllegalInterestRateException(double min, double max) {
        super("The interest rate must be lower than "+max+" but not lower than "+min);
        LOGGER.warn("[ERROR] - IllegalInterestRateException");
    }
}
