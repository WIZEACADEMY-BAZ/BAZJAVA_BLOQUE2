package com.baz.wizeline.learningspring.service;

import com.baz.wizeline.learningspring.model.ResponseDTO;

public interface UserService {

    ResponseDTO createUser(String user, String password);

    ResponseDTO login(String user, String password);
}
