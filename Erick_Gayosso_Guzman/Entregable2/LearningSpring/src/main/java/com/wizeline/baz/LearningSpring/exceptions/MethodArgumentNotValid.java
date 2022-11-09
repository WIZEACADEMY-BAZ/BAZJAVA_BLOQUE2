package com.wizeline.baz.LearningSpring.exceptions;

public class MethodArgumentNotValid extends Exception {

	private static final long serialVersionUID = 7663970576867547367L;

	private final String valor;

	public MethodArgumentNotValid(String valor, String message) {
		super(message);
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}

}
