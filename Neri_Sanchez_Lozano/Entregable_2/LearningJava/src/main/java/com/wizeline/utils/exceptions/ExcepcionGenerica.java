package com.wizeline.utils.exceptions;

@SuppressWarnings("serial")
public class ExcepcionGenerica extends RuntimeException {
	
	public ExcepcionGenerica(String mensajeError) {
		super(mensajeError);
	}

}
