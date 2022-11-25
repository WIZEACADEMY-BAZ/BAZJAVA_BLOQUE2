package com.wizeline.maven.learningjava.Learning.model.tiposNotificacion;

public class EmailNotification implements Notification {

    @Override
    public void notifyUser() {
        LOGGER.info("Enviando notificacion a correo electronico");
    }
}