package com.wizeline.entregabledavid.service;

import com.wizeline.entregabledavid.repository.UserRepository;
import com.wizeline.entregabledavid.repository.UserRepositoryImpl;
import com.wizeline.entregabledavid.model.ErrorDTO;
import com.wizeline.entregabledavid.model.ResponseDTO;
import com.wizeline.entregabledavid.utils.Utils;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public ResponseDTO createUser(String user, String password) {
        LOGGER.info("Inicia procesamiento en capa de negocio");
        ResponseDTO response = new ResponseDTO();
        String result = "fail";
        if (Utils.validateNullValue(user) && Utils.validateNullValue(password)){
            UserRepository userRepository = new UserRepositoryImpl();
            result = userRepository.createUser(user, password);
            response.setCode("OK000");
            response.setStatus(result);
        } else {
            response.setCode("OK000");
            response.setStatus(result);
            response.setErrors(new ErrorDTO("ER001","Error al crear usuario"));
        }
        return response;
    }

    @Override
    public ResponseDTO login(String user, String password) {
        LOGGER.info("Inicia procesamiento en capa de negocio");
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
            response.setErrors(new ErrorDTO("ER001",result));
            response.setStatus("fail");
        }
        return response;
    }
}
