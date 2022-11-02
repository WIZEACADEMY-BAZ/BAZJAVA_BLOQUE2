package com.cursojava.learning2.utils.exceptions;

public class ExcepcionGenerica extends RuntimeException {
    public ExcepcionGenerica(String mensajeError) {
        super(mensajeError);
    }
}
