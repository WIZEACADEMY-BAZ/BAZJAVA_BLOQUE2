package com.wizeline.learningspring.service;

import java.util.logging.Logger;

import com.wizeline.learningspring.LearningspringApplication;
import com.wizeline.learningspring.model.UserDTO;
import com.wizeline.learningspring.utils.Utils;
import org.springframework.stereotype.Service;

import com.wizeline.learningspring.model.ResponseDTO;
import com.wizeline.learningspring.repository.UserRepositoryImpl;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
	private static String textThread = "";
	
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

			textThread = result;
			LOGGER.info(textThread);
			LearningspringApplication thread = new LearningspringApplication();
			thread.start();
			while(thread.isAlive());
		}else {
			response.setCode("OK000");
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
			UserRepositoryImpl userDao = new UserRepositoryImpl();
			result = userDao.login(user, password);
		}
		if("success".equals(result)) {
			response.setCode("OK000");
			response.setStatus(result);

			textThread = result;
			LOGGER.info(textThread);
			LearningspringApplication thread = new LearningspringApplication();
			thread.start();
			while(thread.isAlive());
		} else {
			response.setCode("ER001");
			response.setErrors(new ResponseDTO.ErrorDTO("ER001",result));
			response.setStatus("fail");
		}
		return response;
	}

}
