package com.ironhack.midterm.exceptions;

import com.ironhack.midterm.MidtermApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FraudDetectedException extends RuntimeException {
    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public FraudDetectedException() {
        super("A possible fraud was detected");
        LOGGER.warn("[ERROR] - FraudDetectedException");
    }
}
