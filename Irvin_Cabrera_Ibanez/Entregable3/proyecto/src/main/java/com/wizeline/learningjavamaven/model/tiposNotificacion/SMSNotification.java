package com.wizeline.learningjavamaven.model.tiposNotificacion;

public class SMSNotification implements Notification {

    @Override
    public void notifyUser() {
        LOGGER.info("Enviando notificación por SMS");
    }
}
