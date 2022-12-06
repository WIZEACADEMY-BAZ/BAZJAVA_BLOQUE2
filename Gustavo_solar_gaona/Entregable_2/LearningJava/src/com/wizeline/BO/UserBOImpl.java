package com.wizeline.BO;

import com.wizeline.DAO.UserDAO;
import com.wizeline.DAO.UserDAOImpl;
import com.wizeline.DTO.ErrorDTO;
import com.wizeline.DTO.ResponseDTO;
import com.wizeline.utils.Utils;

import java.util.logging.Logger;

public class UserBOImpl implements UserBO{
    private static final Logger LOGGER = Logger.getLogger(UserDAOImpl.class.getName());

    @Override
    public ResponseDTO createUser(String user, String password){
        LOGGER.info("Inicia procesamiento en capa de negocio");
        ResponseDTO response = new ResponseDTO();
        String result = "fail";
        if(Utils.validateNullValue(user) && Utils.validateNullValue(password)){
            UserDAO userDAO = new UserDAOImpl();
            result = userDAO.createUser(user, password);
            response.setCode("OK000");
            response.setStatus(result);
        }else{
            response.setCode("OK000");
            response.setStatus(result);
            response.setErrors(new ErrorDTO("ER001","Error al crear el usuario"));
        }
        return response;
    }

    @Override
    public ResponseDTO login(String user, String password){
        LOGGER.info("Inicia procesamiento en capa de negocio");
        ResponseDTO response = new ResponseDTO();
        String result = "";
        if(Utils.validateNullValue(user) && Utils.validateNullValue(password)){
            UserDAO userDAO = new UserDAOImpl();
            result = userDAO.login(user, password);
        }
        if("success".equals(result)){
            response.setCode("OK000");
            response.setStatus(result);
        }else{
            response.setCode("ER0001");
            response.setStatus("faild");
            response.setErrors(new ErrorDTO("ER001", result));
        }

        return response;
    }
}
