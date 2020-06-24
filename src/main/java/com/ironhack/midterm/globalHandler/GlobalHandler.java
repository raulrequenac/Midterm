package com.ironhack.midterm.globalHandler;

import com.ironhack.midterm.exceptions.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class GlobalHandler {
    @ExceptionHandler(AlreadyLoggedInException.class)
    public void handleAlreadyLoggedInException(AlreadyLoggedInException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
    }

    @ExceptionHandler(ForbiddenAccessException.class)
    public void handleForbiddenAccessException(ForbiddenAccessException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, e.getMessage());
    }

    @ExceptionHandler(IdNotFoundException.class)
    public void handleIdNotFoundException(IdNotFoundException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(IllegalCreditLimitException.class)
    public void handleIllegalCreditLimitException(IllegalCreditLimitException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(IllegalInterestRateException.class)
    public void handleIllegalInterestRateException(IllegalInterestRateException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(IllegalMinimumBalanceException.class)
    public void handleIllegalMinimumBalanceException(IllegalMinimumBalanceException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(NotEnoughBalanceException.class)
    public void handleNotEnoughBalanceException(NotEnoughBalanceException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(NotLoggedInException.class)
    public void handleNotLoggedInException(NotLoggedInException e, HttpServletResponse response) throws IOException {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, e.getMessage());
    }
}
