package com.wizeline.maven.learninjavamaven.utils;

import com.wizeline.maven.learninjavamaven.model.ResponseDTO;
import com.wizeline.maven.learninjavamaven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommonServices {

    @Autowired
    UserService userService;

    public ResponseDTO login(String user, String password) {
        //UserService UserService = new UserServiceImpl();
        return userService.login(user, password);
    }

}
