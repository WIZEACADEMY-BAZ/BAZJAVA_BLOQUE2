package com.wizeline.learningspring.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wizeline.learningspring.model.ResponseDTO;
import com.wizeline.learningspring.service.UserService;

@Component
public class CommonServices {
    @Autowired
    UserService userService;

    public ResponseDTO login(String user, String password) {
        return userService.login(user, password);
    }

}
