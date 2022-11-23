package com.baz.wizeline.learningspring.service;

import com.baz.wizeline.learningspring.model.ResponseDTO;
import com.baz.wizeline.learningspring.repository.UserDAO;
import com.baz.wizeline.learningspring.repository.UserDAOImpl;
import com.baz.wizeline.learningspring.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserServiceImpl implements  UserService{

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    UserDAO userDAO;

    public UserServiceImpl(@Autowired UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public ResponseDTO createUser(String user, String password) {
        LOGGER.info("Inicia procesamiento en capa de negocio");
        ResponseDTO response = new ResponseDTO();
        String result = "fail";
        if (Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
            result = userDAO.createUser(user, password);
            response.setCode("OK000");
            response.setStatus(result);
        }else {
            response.setCode("OK001");
            response.setStatus(result);
            response.setErrors(new ResponseDTO.ErrorDTO("ER001","Error al crear usuario"));
        }
        return response;
    }

    @Override
    public ResponseDTO login(String user, String password) {
        LOGGER.info("Inicia procesamiento en capa de negocio");
        ResponseDTO response = new ResponseDTO();
        String result = "";
        if (Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
            result = userDAO.login(user, password);
        }
        if("success".equals(result)) {
            response.setCode("OK000");
            response.setStatus(result);
        } else {
            response.setCode("ER001");
            response.setErrors(new ResponseDTO.ErrorDTO("ER001",result));
            response.setStatus("fail");
        }
        return response;
    }

}
