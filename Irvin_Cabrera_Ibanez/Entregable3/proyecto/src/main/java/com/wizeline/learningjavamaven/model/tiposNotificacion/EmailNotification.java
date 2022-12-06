package com.wizeline.learningjavamaven.model.tiposNotificacion;

public class EmailNotification implements Notification {

  @Override
  public void notifyUser() {
    LOGGER.info("Enviando notificacion a correo electronico");
  }
}
