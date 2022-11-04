package com.wizeline.maven.learningjava.Learning.service;
import com.wizeline.maven.learningjava.Learning.model.ResponseDTO;

public interface UserService {


    ResponseDTO createUser(String user, String password);

    ResponseDTO login(String user, String password);

}
