package com.wizeline.maven.learningjava.Learning.model.tiposNotificacion;

public class WhatsNotification implements Notification {

    @Override
    public void notifyUser() {
        LOGGER.info("Enviando notificacíon a whatsapp");
    }
}