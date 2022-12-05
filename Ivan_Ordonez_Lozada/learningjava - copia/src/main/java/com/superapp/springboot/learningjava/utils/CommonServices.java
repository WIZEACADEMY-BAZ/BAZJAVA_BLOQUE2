package com.superapp.springboot.learningjava.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.superapp.springboot.learningjava.dto.ResponseDTO;
import com.superapp.springboot.learningjava.service.UserService;

@Component
public class CommonServices {

    @Autowired
    UserService userService;

    public ResponseDTO login(String user, String password) {
        return userService.login(user, password);
    }

}