package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.ResponseModel;

public interface UserService {
    ResponseModel createUser(String user, String password);
    ResponseModel login(String user, String password);
}
