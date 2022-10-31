package com.wizeline.service;

//import com.wizeline.DTO.ResponseDTO;
import com.wizeline.model.ResponseDTO;

public interface UserBO {

    ResponseDTO createUser(String user, String password);

    ResponseDTO login(String user, String password);

}
