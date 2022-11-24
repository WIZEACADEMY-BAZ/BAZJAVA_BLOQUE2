package com.baz.wizeline.learningspring.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExceptionHandlerAdviceTest {

    @InjectMocks
    ExceptionHandlerAdvice exceptionHandlerAdvice;

    @Test
    @DisplayName("ExceptionHandler")
    void handleAccessDeniedException() {
        AccessDeniedException exception = new AccessDeniedException("No autorizado");
        ResponseEntity<String> response = exceptionHandlerAdvice.handleAccessDeniedException(exception);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

}