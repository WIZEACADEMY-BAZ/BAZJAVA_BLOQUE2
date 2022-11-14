package com.Wizeline.maven.learningjavamaven.service;

import com.Wizeline.maven.learningjavamaven.model.ResponseDTO;

public interface UserService {

    ResponseDTO createUser(String user, String password);

    ResponseDTO login(String user, String password);

}
