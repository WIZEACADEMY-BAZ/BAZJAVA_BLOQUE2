package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.ErrorDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.repository.UserRepository;
import com.wizeline.maven.learningjavamaven.repository.UserRepositoryImpl;
import com.wizeline.maven.learningjavamaven.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;



public class UserServiceImpl implements UserService{
    //private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    UserRepository userRepository;
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    public ResponseDTO createUser(String user, String password) {
        LOGGER.info("Inicia procesamiento en capa de negocio");
        ResponseDTO response = new ResponseDTO();
        String result = "fail";
        if (Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
            UserRepository userDao = new UserRepositoryImpl();
            result = userDao.createUser(user, password);
            response.setCode("OK000");
            response.setStatus(result);
        }else {
            response.setCode("OK000");
            response.setStatus(result);

            response.setErrors(new ErrorDTO.ErrorDTOBuilder().errorCode("ER001").message("Error al crear usuario").build());
        }
        return response;
    }

    @Override
    public ResponseDTO login(String user, String password) {
        LOGGER.info("Inicia procesamiento en capa de negocio");
        ResponseDTO response = new ResponseDTO();
        String result = "";
        if (Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
            UserRepository userDao = new UserRepositoryImpl();
            result = userDao.login(user, password);
        }
        if("success".equals(result)) {
            response.setCode("OK000");
            response.setStatus(result);
        } else {
            response.setCode("ER001");
            //response.setErrors(new ErrorDTO.ErroDTOBilder("ER001",result));
            response.setErrors(new ErrorDTO.ErrorDTOBuilder().errorCode("ER001").message("Error al crear usuario").build());

            response.setStatus("fail");
        }
        return response;
    }
}
