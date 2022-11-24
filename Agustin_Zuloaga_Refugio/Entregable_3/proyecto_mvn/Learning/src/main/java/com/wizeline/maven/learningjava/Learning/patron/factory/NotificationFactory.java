package com.wizeline.maven.learningjava.Learning.patron.factory;

import com.wizeline.maven.learningjava.Learning.model.tiposNotificacion.*;

public class NotificationFactory {

    public Notification createNotification(String tipo) {
        if (tipo == null || tipo.isEmpty()) {
            return null;
        }
        switch (tipo) {
            case "SMS":
                return new SMSNotification();
            case "EMAIL":
                return new EmailNotification();
            case "PUSH":
                return new PushNotification();
            case "WHATS":
                return new WhatsNotification();
            default:
                throw new IllegalArgumentException("Tipo desconido " + tipo);
        }
    }
}
