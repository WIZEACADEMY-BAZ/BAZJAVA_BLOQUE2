package com.wizeline.maven.learningjavamaven.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.nio.file.AccessDeniedException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ExceptionHandlerAdviceTest {

    @InjectMocks
    private ExceptionHandlerAdvice exceptionHandlerAdvice;

    @BeforeEach()
    void init(){
        System.out.println("@BeforeEach => iniciando");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleAccessDeniedExceptionTest(){
        System.out.println("@Test => handleAccessDeniedExceptionTest()");
        //assertEquals(exceptionHandlerAdvice.handleAccessDeniedException(any(AccessDeniedException.class)).getBody(), "Acceso denegado");
    }

}