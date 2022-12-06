package com.wizeline.baz.LearningSpring.service;

import com.wizeline.baz.LearningSpring.model.ResponseDTO;

public interface UserService {

    ResponseDTO createUser(String user, String password);

    ResponseDTO login(String user, String password);
}
