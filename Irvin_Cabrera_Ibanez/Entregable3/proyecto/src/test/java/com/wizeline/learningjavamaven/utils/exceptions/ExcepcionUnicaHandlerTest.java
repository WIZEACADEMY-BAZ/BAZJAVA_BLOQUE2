package com.wizeline.learningjavamaven.utils.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ExcepcionUnicaHandlerTest {

  @InjectMocks
  ExcepcionUnicaHandler excepcionUnicaHandler;

  @Test
  void handlerApiErrorExceptionUnica() {
    ExcepcionUnica exception = new ExcepcionUnica();
    ResponseEntity<String> response = excepcionUnicaHandler.handlerApiErrorException(exception);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void handlerApiErrorException() {
    ExcepcionUnica exception = new ExcepcionUnica(500);
    ResponseEntity<String> response = excepcionUnicaHandler.handlerApiErrorException(exception);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void handlerApiErrorExceptionHttp() {
    ExcepcionUnica exception = new ExcepcionUnica(500, HttpStatus.FORBIDDEN);
    ResponseEntity<String> response = excepcionUnicaHandler.handlerApiErrorException(exception);
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }

  @Test
  void handlerApiErrorExceptionList() {
    ExcepcionUnica exception = new ExcepcionUnica(500, Collections.singletonList("Error"));
    ResponseEntity<String> response = excepcionUnicaHandler.handlerApiErrorException(exception);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void handlerApiErrorExceptionListEmpty() {
    ExcepcionUnica exception = new ExcepcionUnica(500, new ArrayList<>());
    ResponseEntity<String> response = excepcionUnicaHandler.handlerApiErrorException(exception);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void handlerApiErrorExceptionListHttp() {
    ExcepcionUnica exception = new ExcepcionUnica(500, Collections.singletonList("Error"), HttpStatus.FORBIDDEN);
    ResponseEntity<String> response = excepcionUnicaHandler.handlerApiErrorException(exception);
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
  }
}