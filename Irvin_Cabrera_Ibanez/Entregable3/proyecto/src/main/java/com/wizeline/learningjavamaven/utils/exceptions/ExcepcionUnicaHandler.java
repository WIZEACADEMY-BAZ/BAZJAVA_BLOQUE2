package com.wizeline.learningjavamaven.utils.exceptions;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExcepcionUnicaHandler {

  @ExceptionHandler(ExcepcionUnica.class)
  public ResponseEntity<String> handlerApiErrorException(ExcepcionUnica ex) {
    String mensaje = "Ocurrio un error " + ex.getCodigoError() + ", con el estatus " + ex.getHttpStatus();
    String detalle = ex.getDetalles() != null && !ex.getDetalles().isEmpty() ? " mensaje " + ex.getDetalles().get(0) : "";
    return new ResponseEntity<>(mensaje + detalle, ex.getHttpStatus());
  }
}