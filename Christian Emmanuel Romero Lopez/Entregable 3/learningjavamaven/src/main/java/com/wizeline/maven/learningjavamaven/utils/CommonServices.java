package com.wizeline.maven.learningjavamaven.utils;

import com.wizeline.maven.learningjavamaven.model.ResponseModel;
import com.wizeline.maven.learningjavamaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommonServices {

    @Autowired
    UserService userService;

    public ResponseModel login(String user, String password) {
        return userService.login(user, password);
    }
}
