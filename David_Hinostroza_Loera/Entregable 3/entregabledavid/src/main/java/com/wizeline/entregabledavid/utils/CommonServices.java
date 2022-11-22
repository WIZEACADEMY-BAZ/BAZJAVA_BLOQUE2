package com.wizeline.entregabledavid.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wizeline.entregabledavid.model.ResponseDTO;
import com.wizeline.entregabledavid.service.UserService;

@Component
public class CommonServices {

    @Autowired
    UserService userService;

    public ResponseDTO login(String user, String password) {
        return userService.login(user, password);
    }

}