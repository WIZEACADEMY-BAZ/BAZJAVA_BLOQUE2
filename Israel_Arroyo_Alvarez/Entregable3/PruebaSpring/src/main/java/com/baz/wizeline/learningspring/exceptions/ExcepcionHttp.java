package com.baz.wizeline.learningspring.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;
import java.util.List;

import java.util.List;

public class ExcepcionHttp extends RuntimeException {

    private int codigoError;
    private List<String> detalles;
    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;


  public ExcepcionHttp(int codigoError, @Nullable List<String> detalles) {
        this.codigoError = codigoError;
        this.detalles = detalles;
    }


    public int getCodigoError() {
        return codigoError;
    }


    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
