package com.wizeline.maven.learninjavamaven.service;

import com.wizeline.maven.learninjavamaven.model.ResponseDTO;

public interface UserService {

    ResponseDTO createUser(String user, String password);

    ResponseDTO login (String user, String password);
}
