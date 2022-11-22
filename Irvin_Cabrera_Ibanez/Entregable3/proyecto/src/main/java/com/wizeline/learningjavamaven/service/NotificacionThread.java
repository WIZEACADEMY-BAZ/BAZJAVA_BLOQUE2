package com.wizeline.learningjavamaven.service;

import com.wizeline.learningjavamaven.model.detalle.UserDescription;
import com.wizeline.learningjavamaven.model.tiposNotificacion.Notification;
import com.wizeline.learningjavamaven.patron.factory.NotificationFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.logging.Logger;

public class NotificacionThread extends Thread {

  private static final Logger LOGGER = Logger.getLogger(NotificacionThread.class.getName());

  UserDescription user;
  Instant inicioDeEjecucion;
  String tipoMensaje;

  public NotificacionThread(UserDescription user, Instant inicioDeEjecucion, String tipoMensaje) {
    this.user = user;
    this.inicioDeEjecucion = inicioDeEjecucion;
    this.tipoMensaje = tipoMensaje;
  }

  @Override
  public void run() {
    try {
      // sleep simula el tiempo de respuesta de un servicio
      sleep(1000);
      Instant finalDeEjecucion = Instant.now();
      String total = new String(String.valueOf(Duration.between(inicioDeEjecucion, finalDeEjecucion).toMillis()).concat(" segundos."));
      LOGGER.info("Envio y validación de usuario de notificación de consulta " + user.getName());
      notificacion();
      LOGGER.info("Tiempo de envio: ".concat(total));
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void notificacion() {
    NotificationFactory notificationFactory = new NotificationFactory();
    Notification notification = notificationFactory.createNotification(tipoMensaje);
    notification.notifyUser();
  }
}
