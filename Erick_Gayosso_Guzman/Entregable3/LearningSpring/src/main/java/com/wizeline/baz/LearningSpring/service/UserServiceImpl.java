package com.wizeline.baz.LearningSpring.service;

import java.util.logging.Logger;
import com.wizeline.baz.LearningSpring.model.ErrorDTO;
import com.wizeline.baz.LearningSpring.model.ResponseDTO;
import com.wizeline.baz.LearningSpring.repository.UserRepositoryImpl;
import com.wizeline.baz.LearningSpring.utils.Utils;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public com.wizeline.baz.LearningSpring.model.ResponseDTO createUser(String user, String password) {
        LOGGER.info("Inicia procesamiento en capa de negocio");
        ResponseDTO response = new ResponseDTO();
        String result = "fail";
        if (Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
            UserRepositoryImpl userDao = new UserRepositoryImpl();
            result = userDao.createUser(user, password);
            response.setCode("OK000");
            response.setStatus(result);
        }else {
            response.setCode("OK000");
            response.setStatus(result);
            response.setErrors(new ErrorDTO("ER001","Error al crear usuario"));
        }
        return response;
    }

    @Override
    public com.wizeline.baz.LearningSpring.model.ResponseDTO login(String user, String password) {
        LOGGER.info("Inicia procesamiento en capa de negocio");
        ResponseDTO response = new ResponseDTO();
        String result = "";
        if (Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
            UserRepositoryImpl userDao = new UserRepositoryImpl();
            result = userDao.login(user, password);
        }
        if("success".equals(result)) {
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
