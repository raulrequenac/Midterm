package com.ironhack.midterm.globalHandler;

import com.ironhack.midterm.exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GlobalHandlerTest {
    private GlobalHandler globalHandler = new GlobalHandler();

    private MockHttpServletResponse response;

    @BeforeEach
    public void setUp() {
        response = new MockHttpServletResponse();
    }

    @Test
    public void handleAlreadyActiveException() throws IOException {
        globalHandler.handleAlreadyActiveException(new AlreadyActiveException(), response);
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    public void handleAlreadyLoggedInException() throws IOException {
        globalHandler.handleAlreadyLoggedInException(new AlreadyLoggedInException(), response);
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    public void handleAlreadyLoggedOutException() throws IOException {
        globalHandler.handleAlreadyLoggedOutException(new AlreadyLoggedOutException(), response);
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    public void handleForbiddenAccessException() throws IOException {
        globalHandler.handleForbiddenAccessException(new ForbiddenAccessException(), response);
        assertEquals(HttpStatus.UNAUTHORIZED.value(), response.getStatus());
    }

    @Test
    public void handleFraudDetectedException() throws IOException {
        globalHandler.handleFraudDetectedException(new FraudDetectedException(), response);
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    public void handleFrozenAccountException() throws IOException {
        globalHandler.handleFrozenAccountException(new FrozenAccountException(), response);
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }

    @Test
    public void handleIdNotFoundException() throws IOException {
        globalHandler.handleIdNotFoundException(new IdNotFoundException("test", 1), response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void handleIllegalCreditLimitException() throws IOException {
        globalHandler.handleIllegalCreditLimitException(new IllegalCreditLimitException(), response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void handleIllegalInterestRateException() throws IOException {
        globalHandler.handleIllegalInterestRateException(new IllegalInterestRateException(0, 0), response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void handleIllegalMinimumBalanceException() throws IOException {
        globalHandler.handleIllegalMinimumBalanceException(new IllegalMinimumBalanceException(), response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void handleIllegalSecretKeyException() throws IOException {
        globalHandler.handleIllegalSecretKeyException(new IllegalSecretKeyException(), response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void handleNameNotFoundException() throws IOException {
        globalHandler.handleNameNotFoundException(new NameNotFoundException(), response);
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }

    @Test
    public void handleNotEnoughBalanceException() throws IOException {
        globalHandler.handleNotEnoughBalanceException(new NotEnoughBalanceException(), response);
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void handleNotLoggedInException() throws IOException {
        globalHandler.handleNotLoggedInException(new NotLoggedInException(), response);
        assertEquals(HttpStatus.FORBIDDEN.value(), response.getStatus());
    }
}