package com.wizeline.baz.LearningSpring.utils;

import com.wizeline.baz.LearningSpring.model.ResponseDTO;
import com.wizeline.baz.LearningSpring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CommonServices {

    @Autowired
    UserService userService;

    public ResponseDTO login(String user, String password) {
        //UserService UserService = new UserServiceImpl();
        return userService.login(user, password);
    }
}
