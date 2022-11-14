package com.wizeline.maven.learningjavamaven.utils.exceptions;

// Revisión: Herencia en una de sus clases
public class ExcepcionGenerica extends RuntimeException {
  public ExcepcionGenerica(String mensajeError) {
    super(mensajeError);
  }
}