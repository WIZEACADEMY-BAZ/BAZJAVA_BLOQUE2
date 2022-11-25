package com.wizeline.maven.learninjavamaven.utils.exceptions;

public class ExceptionGenerica extends RuntimeException{
    public ExceptionGenerica(String mensajeError){
        super(mensajeError);
    }

    //Sobrecarga de metodo
    public ExceptionGenerica(){
        super();
    }
}
