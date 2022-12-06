package com.wizeline.maven.learningjava.Learning.service;

import java.util.logging.Logger;


import org.springframework.stereotype.Service;
import com.wizeline.maven.learningjava.Learning.model.UserDateDTO;
import com.wizeline.maven.learningjava.Learning.model.ErrorDTO;
import com.wizeline.maven.learningjava.Learning.model.ResponseDTO;
import com.wizeline.maven.learningjava.Learning.patron.Iterator.UserDateDTOCollection;
import com.wizeline.maven.learningjava.Learning.patron.Iterator.UserDateDTOCollectionImpl;
import com.wizeline.maven.learningjava.Learning.patron.Iterator.Iterator;
import com.wizeline.maven.learningjava.Learning.repository.UserRepositoryImpl;
import com.wizeline.maven.learningjava.Learning.utils.Utils;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

    @Override
    public ResponseDTO createUser(String user, String password) {
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
    public ResponseDTO login(String user, String password) {
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
    @Override
    public List<UserDateDTO> getUserDateIterator(String userId){
        List<UserDateDTO> userDateDTOList = new ArrayList<>();
        UserDateDTOCollection users = fechasUsuarios();

        Iterator baseIterator = users.iterator(userId);
        while(baseIterator.hasNext()){
            UserDateDTO userDateDTO = baseIterator.next();
            System.out.println(userDateDTO.toString());
            userDateDTOList.add(userDateDTO);
        }
        System.out.println("**********");

        return userDateDTOList;
    }

    private static UserDateDTOCollection fechasUsuarios() {
        UserDateDTOCollection userDateDTOCollection = new UserDateDTOCollectionImpl();
        userDateDTOCollection.addUserDate(new UserDateDTO("1", "12", "10-01-1990"));
        userDateDTOCollection.addUserDate(new UserDateDTO("2", "18", "10-01-1990"));
        userDateDTOCollection.addUserDate(new UserDateDTO("3", "22", "10-01-1990"));
        userDateDTOCollection.addUserDate(new UserDateDTO("4", "19", "10-01-1990"));
        userDateDTOCollection.addUserDate(new UserDateDTO("5", "35", "10-01-1990"));
        userDateDTOCollection.addUserDate(new UserDateDTO("6", "29", "10-01-1990"));
        userDateDTOCollection.addUserDate(new UserDateDTO("7", "32", "10-01-1990"));
        userDateDTOCollection.addUserDate(new UserDateDTO("8", "40", "10-01-1990"));
        userDateDTOCollection.addUserDate(new UserDateDTO("9", "25", "10-01-1990"));
        return userDateDTOCollection;
    }
}