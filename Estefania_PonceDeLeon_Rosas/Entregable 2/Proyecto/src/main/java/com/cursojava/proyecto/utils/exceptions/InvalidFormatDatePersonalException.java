package com.cursojava.proyecto.utils.exceptions;

public class InvalidFormatDatePersonalException extends RuntimeException {
    public InvalidFormatDatePersonalException(String mensajeError) {
        super(mensajeError);
    }
}