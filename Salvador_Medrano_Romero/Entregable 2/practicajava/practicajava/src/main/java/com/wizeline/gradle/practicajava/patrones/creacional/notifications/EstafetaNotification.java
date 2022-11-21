package com.wizeline.gradle.practicajava.patrones.creacional.notifications;

public class EstafetaNotification implements Notification {

	@Override
	public void notifyUser() {
		System.out.println("Enviando paquete por Estafeta ...");
	}

}
