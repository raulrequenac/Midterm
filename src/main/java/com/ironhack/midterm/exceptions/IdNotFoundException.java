package com.ironhack.midterm.exceptions;

import com.ironhack.midterm.MidtermApplication;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IdNotFoundException extends RuntimeException {
    private static final Logger LOGGER = LogManager.getLogger(MidtermApplication.class);

    public IdNotFoundException(String entity, Integer id) {
        super("There is no "+entity+" with id "+id);
        LOGGER.warn("[ERROR] - IdNotFoundException");
    }
}
