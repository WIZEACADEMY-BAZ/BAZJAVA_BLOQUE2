package com.wizeline.learningjavamaven.utils.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ExpiredHandlerTest {

  @InjectMocks
  ExpiredHandler expiredHandler;

  @Test
  void handlerApiExpired() {
    ExpiredJwtException exception = new ExpiredJwtException(null, null, "mensaje");
    ResponseEntity<String> response = expiredHandler.handlerApiExpired(exception);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }
}