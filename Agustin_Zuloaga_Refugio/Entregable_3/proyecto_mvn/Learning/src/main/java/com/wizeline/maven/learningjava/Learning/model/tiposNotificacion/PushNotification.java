package com.wizeline.maven.learningjava.Learning.model.tiposNotificacion;

public class PushNotification implements Notification {

    @Override
    public void notifyUser() {
        LOGGER.info("Enviando notificación push");
    }
}
