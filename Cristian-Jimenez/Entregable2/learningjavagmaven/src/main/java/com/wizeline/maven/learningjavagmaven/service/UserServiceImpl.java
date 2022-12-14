
package com.wizeline.maven.learningjavagmaven.service;

import com.wizeline.maven.learningjavagmaven.model.ErrorModel;
import com.wizeline.maven.learningjavagmaven.model.ResponseModel;
import com.wizeline.maven.learningjavagmaven.repository.BankingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import com.wizeline.maven.learningjavagmaven.repository.UserRepository;
import com.wizeline.maven.learningjavagmaven.repository.UserImpl;
import com.wizeline.maven.learningjavagmaven.utils.Utils;

import java.util.logging.Logger;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public ResponseModel createUser(String user, String password) {
        LOGGER.info("Inicia procesamiento en capa de negocio");
        ResponseModel response = new ResponseModel();
        String result = "fail";
        if (Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
            UserRepository userRepository = new UserImpl();
            result = userRepository.createUser(user, password);
            response.setCode("OK000");
            response.setStatus(result);
        }else {
            response.setCode("OK000");
            response.setStatus(result);
            response.setErrors(new ErrorModel("ER001","Error al crear usuario"));
        }
        return response;
    }

    @Override
    public ResponseModel login(String user, String password) {
        LOGGER.info("Inicia procesamiento en capa de negocio");
        ResponseModel response = new ResponseModel();
        String result = "";
        if (Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
            UserRepository userRepository = new UserImpl();
            result = userRepository.login(user, password);
        }
        if("success".equals(result)) {
            response.setCode("OK000");
            response.setStatus(result);
        } else {
            response.setCode("ER001");
            response.setErrors(new ErrorModel("ER001",result));
            response.setStatus("fail");
        }
        return response;
    }
}