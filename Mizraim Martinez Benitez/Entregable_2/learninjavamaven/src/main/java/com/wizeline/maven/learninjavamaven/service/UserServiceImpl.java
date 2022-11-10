package com.wizeline.maven.learninjavamaven.service;

import com.wizeline.maven.learninjavamaven.model.ErrorDTO;
import com.wizeline.maven.learninjavamaven.model.ResponseDTO;
import com.wizeline.maven.learninjavamaven.repository.UserRepository;
import com.wizeline.maven.learninjavamaven.repository.UserRepositoryImpl;
import com.wizeline.maven.learninjavamaven.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public ResponseDTO createUser(String user, String password) {
        LOGGER.info("Inicia procesamiento en capa de negocio createUser");
        ResponseDTO response = new ResponseDTO();
        String result = "fail";
        if(Utils.validateNullValue(user) && Utils.validateNullValue(password)){
            UserRepository userRepository = new UserRepositoryImpl();
            result = userRepository.createUser(user,password);
            response.setCode("OK000");
            response.setStatus(result);
        } else {
            response.setStatus("OK000");
            response.setStatus(result);
            response.setErrores(new ErrorDTO("ER001", "Error al crear usuario"));
        }
        return response;
    }

    @Override
    public ResponseDTO login(String user, String password) {
        LOGGER.info("Inicia procesamiento en capa de negocio Login");
        ResponseDTO response = new ResponseDTO();
        String result = "";
        if (Utils.validateNullValue(user) && Utils.validateNullValue(password)){
            UserRepository userRepository = new UserRepositoryImpl();
            result = userRepository.login(user, password);
        }
        if ("success".equals(result)){
            response.setCode("OK000");
            response.setStatus(result);
        } else {
            response.setCode("ER001");
            response.setErrores(new ErrorDTO("ER001", result));
            response.setStatus("fail");
        }
        return response;
    }
}
