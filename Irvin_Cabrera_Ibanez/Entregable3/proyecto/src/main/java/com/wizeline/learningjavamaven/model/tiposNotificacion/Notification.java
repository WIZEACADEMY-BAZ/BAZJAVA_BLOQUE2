package com.wizeline.learningjavamaven.model.tiposNotificacion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Notification {

  public static final Logger LOGGER = LoggerFactory.getLogger(Notification.class);

  void notifyUser();
}
