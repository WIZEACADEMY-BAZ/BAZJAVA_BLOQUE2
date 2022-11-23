package com.wizeline.learningjavamaven.patron.factory;

import com.wizeline.learningjavamaven.model.tiposNotificacion.Notification;
import com.wizeline.learningjavamaven.utils.exceptions.ExcepcionUnica;
import org.aspectj.weaver.ast.Instanceof;
import org.aspectj.weaver.ast.Not;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotificationFactoryTest {

  @InjectMocks
  NotificationFactory notificationFactory;

  @Test
  @DisplayName("Notificacion nula")
  void createNotificationNull() {
    Notification noti = notificationFactory.createNotification(null);
    assertNull(noti);
  }

  @Test
  @DisplayName("Notificacion vacia")
  void createNotificationEmpty() {
    Notification noti = notificationFactory.createNotification("");
    assertNull(noti);
  }

  @Test
  @DisplayName("Notificacion Mensaje")
  void createNotificationSMS() {
    Notification noti = notificationFactory.createNotification("SMS");
    noti.notifyUser();
  }

  @Test
  @DisplayName("Notificacion Correo")
  void createNotificationEMAIL() {
    Notification noti = notificationFactory.createNotification("EMAIL");
    noti.notifyUser();
  }

  @Test
  @DisplayName("Notificacion Push")
  void createNotificationPUSH() {
    Notification noti = notificationFactory.createNotification("PUSH");
    noti.notifyUser();
  }

  @Test
  @DisplayName("Notificacion Whatsapp")
  void createNotificationWHATS() {
    Notification noti = notificationFactory.createNotification("WHATS");
    noti.notifyUser();
  }

  @Test
  @DisplayName("Notificacion error")
  void createNotificationError() {
    IllegalArgumentException thrown = assertThrows(
        IllegalArgumentException.class,
        () -> notificationFactory.createNotification("ASD"),
        "Envio Incorrecto"
    );
    assertEquals("Tipo desconido ASD", thrown.getMessage());
  }
}