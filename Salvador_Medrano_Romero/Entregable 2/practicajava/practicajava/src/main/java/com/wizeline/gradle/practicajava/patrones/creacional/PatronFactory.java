package com.wizeline.gradle.practicajava.patrones.creacional;

import com.wizeline.gradle.practicajava.patrones.creacional.factory.EnviaProductoFactory;
import com.wizeline.gradle.practicajava.patrones.creacional.notifications.Notification;

public class PatronFactory {
	
	public static void main(String[] args) {
        EnviaProductoFactory enviaProductoFactory = new EnviaProductoFactory();
        Notification notification = enviaProductoFactory.enviaProducto("DHL");
        notification.notifyUser();
    }

}
