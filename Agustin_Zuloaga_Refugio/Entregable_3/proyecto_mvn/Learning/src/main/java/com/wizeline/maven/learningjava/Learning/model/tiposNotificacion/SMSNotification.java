package com.wizeline.maven.learningjava.Learning.model.tiposNotificacion;

public class SMSNotification implements Notification {

    @Override
    public void notifyUser() {
        LOGGER.info("Enviando notificación por SMS");
    }
}