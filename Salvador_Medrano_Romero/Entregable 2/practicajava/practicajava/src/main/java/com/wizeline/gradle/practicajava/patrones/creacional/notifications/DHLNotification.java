package com.wizeline.gradle.practicajava.patrones.creacional.notifications;

public class DHLNotification implements Notification {

	@Override
	public void notifyUser() {
		System.out.println("Enviando paquete por DHL ...");
	}

}
