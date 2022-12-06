package utils.exceptions;

public class ExcepcionGenerica extends RuntimeException {
  public ExcepcionGenerica(String mensajeError) {
    super(mensajeError);
  }
}