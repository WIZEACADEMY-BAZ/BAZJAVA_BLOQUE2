package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.BankAccountModel;
import com.wizeline.maven.learningjavamaven.model.UserModel;
import com.wizeline.maven.learningjavamaven.repository.UserAccountRepository;
import com.wizeline.maven.learningjavamaven.model.ErrorModel;
import com.wizeline.maven.learningjavamaven.model.ResponseModel;
import com.wizeline.maven.learningjavamaven.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static com.wizeline.maven.learningjavamaven.utils.Utils.cifrarDato;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserAccountRepository userAccountRepository;

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public ResponseModel createUser(String user, String password) {
        ResponseModel response = new ResponseModel();
        LOGGER.info("Inicia procesamiento en capa de negocio.");
        if (Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
            UserModel userRepository = buildUserAccouunt(user, cifrarDato(password) );
            mongoTemplate.save(userRepository);
            response.setCode("OK000");
            response.setStatus("success");
        } else {
            response.setCode("OK000");
            response.setStatus("fail");
            response.setErrors(new ErrorModel("ERR001","Error al crear usuario."));
        }
        return response;
    }

    @Override
    public ResponseModel login(String user, String password){
        ResponseModel response = new ResponseModel();
        LOGGER.info( "Inicia procesamiento de capa de negocio.");

        Query query = new Query();
        query.addCriteria(Criteria.where("user").is(user));
        query.addCriteria(Criteria.where("password").is(password));
        UserModel userModel = mongoTemplate.findOne(query, UserModel.class);
        response.setUserModel( userModel );
        response.setCode("OK000");
        response.setStatus("success");
        return response;
    }

    private UserModel buildUserAccouunt(String user, String password){
        UserModel userModel = new UserModel();
        userModel.setUser(user);
        userModel.setPassword(password);
        return userModel;
    }
}
