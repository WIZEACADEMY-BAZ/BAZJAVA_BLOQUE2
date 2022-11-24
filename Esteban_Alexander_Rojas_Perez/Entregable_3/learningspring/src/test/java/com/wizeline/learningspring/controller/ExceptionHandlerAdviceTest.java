package com.wizeline.learningspring.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class ExceptionHandlerAdviceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerAdviceTest.class);
    @InjectMocks
    ExceptionHandlerAdvice exceptionHandlerAdvice;

    @Test
    void handleAccessDeniedException() {
        LOGGER.info("handleAccessDeniedExceptionTest");
        AccessDeniedException exception = new AccessDeniedException("No autorizado");
        assertAll(
                () -> assertNotNull(exceptionHandlerAdvice.handleAccessDeniedException(exception)),
                () -> assertEquals(exceptionHandlerAdvice.handleAccessDeniedException(exception).getStatusCode().value(),
                        HttpStatus.FORBIDDEN.value())
        );
    }
}