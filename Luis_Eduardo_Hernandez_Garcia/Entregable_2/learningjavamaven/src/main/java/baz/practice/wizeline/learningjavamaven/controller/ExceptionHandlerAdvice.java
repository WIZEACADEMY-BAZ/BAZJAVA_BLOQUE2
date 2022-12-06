package baz.practice.wizeline.learningjavamaven.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        return new ResponseEntity<>("Acceso denegado", HttpStatus.FORBIDDEN);
    }

}