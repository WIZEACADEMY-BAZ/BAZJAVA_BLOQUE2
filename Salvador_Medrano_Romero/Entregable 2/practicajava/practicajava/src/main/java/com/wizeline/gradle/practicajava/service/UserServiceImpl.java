package com.wizeline.gradle.practicajava.service;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.wizeline.gradle.practicajava.model.ErrorDTO;
import com.wizeline.gradle.practicajava.model.ResponseDTO;
import com.wizeline.gradle.practicajava.repository.UserRepository;
import com.wizeline.gradle.practicajava.repository.UserRepositoryImpl;
import com.wizeline.gradle.practicajava.utils.Utils;

@Service
public class UserServiceImpl implements UserService {
	
	public static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

	@Override
	public ResponseDTO createUser(String user, String password) {
		LOGGER.info("Inicia procesamiento en capa de negocio");
		ResponseDTO response = new ResponseDTO();
		String result = "fail";
		if(Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
			UserRepository userDAO = new UserRepositoryImpl();
			result = userDAO.createUser(user, password);
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
		if(Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
			UserRepository userDAO = new UserRepositoryImpl();
			result = userDAO.login(user, password);
		}
		if("success".equals(result)){
			response.setCode("OK000");
			response.setStatus(result);
		}else {
			response.setCode("ER001");
			response.setErrors(new ErrorDTO("ER001",result));
			response.setStatus("fail");
		}
		return response;
	}

}
