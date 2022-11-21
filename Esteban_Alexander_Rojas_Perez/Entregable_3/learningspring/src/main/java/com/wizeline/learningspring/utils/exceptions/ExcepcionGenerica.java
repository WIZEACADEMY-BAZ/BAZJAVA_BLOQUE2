package com.wizeline.learningspring.utils.exceptions;

public class ExcepcionGenerica extends RuntimeException {
    public ExcepcionGenerica(String mensajeError) {
        super(mensajeError);
    }
}
