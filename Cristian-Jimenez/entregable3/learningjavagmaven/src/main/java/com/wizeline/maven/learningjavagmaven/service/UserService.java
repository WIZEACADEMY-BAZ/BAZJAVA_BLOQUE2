package com.wizeline.maven.learningjavagmaven.service;

import com.wizeline.maven.learningjavagmaven.model.ResponseModel;
public interface UserService {

    ResponseModel createUser(String user, String password);

    ResponseModel login(String user, String password);




}