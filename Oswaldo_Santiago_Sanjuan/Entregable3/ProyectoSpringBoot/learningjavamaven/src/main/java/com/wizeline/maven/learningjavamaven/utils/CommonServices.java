package com.wizeline.maven.learningjavamaven.utils;

import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonServices {
    @Autowired
    UserService userService;

    public ResponseDTO login(String user, String password) {
        return userService.login(user, password);
    }
}
