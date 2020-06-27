package com.ironhack.midterm.exceptions;

import com.ironhack.midterm.MidtermApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NotEnoughBalanceException extends RuntimeException {
    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public NotEnoughBalanceException() {
        super("There's not enough balance in the account");
        LOGGER.warn("[ERROR] - NotEnoughBalanceException");
    }
}
