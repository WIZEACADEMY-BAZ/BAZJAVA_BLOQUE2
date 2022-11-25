package com.wizeline.gradle.practicajava.patrones.creacional.notifications;

public class FedExNotification implements Notification {

	@Override
	public void notifyUser() {
		System.out.println("Enviando paquete por FedEx ...");
	}

}
