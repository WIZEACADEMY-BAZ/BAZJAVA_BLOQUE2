package com.wizeline.gradle.learningjavagradle.service;

import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;

public interface UserService {
    ResponseDTO createUser(String user, String password);
    ResponseDTO login(String user, String password);
}
