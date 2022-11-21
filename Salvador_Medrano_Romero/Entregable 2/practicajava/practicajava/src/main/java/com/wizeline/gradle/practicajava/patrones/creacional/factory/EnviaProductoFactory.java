package com.wizeline.gradle.practicajava.patrones.creacional.factory;

import com.wizeline.gradle.practicajava.patrones.creacional.notifications.DHLNotification;
import com.wizeline.gradle.practicajava.patrones.creacional.notifications.EstafetaNotification;
import com.wizeline.gradle.practicajava.patrones.creacional.notifications.FedExNotification;
import com.wizeline.gradle.practicajava.patrones.creacional.notifications.Notification;

public class EnviaProductoFactory {
	
	public Notification enviaProducto(String channel) {
        if (channel == null || channel.isEmpty())
            return null;
        switch (channel) {
            case "DHL":
                return new DHLNotification();
            case "Estafeta":
                return new EstafetaNotification();
            case "FedEx":
                return new FedExNotification();
            default:
                throw new IllegalArgumentException("Paqueteria desconocida " + channel);
        }
    }

}
