package com.wizeline.learningjavamaven.model.tiposNotificacion;

public class WhatsNotification implements Notification {

  @Override
  public void notifyUser() {
    LOGGER.info("Enviando notificacíon a whatsapp");
  }
}
