package com.wizeline.gradle.learningjavagradle.utils.exceptions;

import org.springframework.http.HttpStatus;

public class NotificationsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotificationsException(String mensaje, HttpStatus badRequest) {
		super(mensaje);
	}
}
