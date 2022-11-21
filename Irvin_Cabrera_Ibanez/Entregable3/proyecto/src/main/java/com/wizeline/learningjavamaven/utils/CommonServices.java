package com.wizeline.learningjavamaven.utils;

import com.wizeline.learningjavamaven.model.ResponseDTO;
import com.wizeline.learningjavamaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonServices {

  @Autowired
  UserService userService;

  public ResponseDTO login(String username, String password) {
    return userService.login(username, password);
  }
}
