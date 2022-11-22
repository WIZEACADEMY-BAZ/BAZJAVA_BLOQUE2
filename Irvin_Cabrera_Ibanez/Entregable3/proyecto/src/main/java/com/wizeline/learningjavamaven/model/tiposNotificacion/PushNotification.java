package com.wizeline.learningjavamaven.model.tiposNotificacion;

public class PushNotification implements Notification {

    @Override
    public void notifyUser() {
        LOGGER.info("Enviando notificación push");
    }
}
