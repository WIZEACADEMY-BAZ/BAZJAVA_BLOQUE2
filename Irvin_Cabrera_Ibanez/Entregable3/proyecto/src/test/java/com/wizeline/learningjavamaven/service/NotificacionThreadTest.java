package com.wizeline.learningjavamaven.service;

import com.wizeline.learningjavamaven.model.detalle.UserDescription;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class NotificacionThreadTest {

  @InjectMocks
  NotificacionThread notificacionThread;

  @Test
  void run() {
    Instant inicioDeEjecucion = Instant.now();
    UserDescription userDescription = new UserDescription();
    userDescription.setUsername("user");
    notificacionThread = new NotificacionThread(userDescription, inicioDeEjecucion, "SMS");
    notificacionThread.run();
  }
}