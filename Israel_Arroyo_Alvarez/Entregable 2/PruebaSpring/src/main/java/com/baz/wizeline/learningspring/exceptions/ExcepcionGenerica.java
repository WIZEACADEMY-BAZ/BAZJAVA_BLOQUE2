package com.baz.wizeline.learningspring.exceptions;

public class ExcepcionGenerica extends RuntimeException {
    public ExcepcionGenerica(String mensajeError) {
        super(mensajeError);
    }
}