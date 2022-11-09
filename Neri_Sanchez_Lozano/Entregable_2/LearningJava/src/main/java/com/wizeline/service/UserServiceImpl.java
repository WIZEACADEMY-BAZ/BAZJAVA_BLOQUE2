package com.wizeline.service;

import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.wizeline.model.ErrorDTO;
import com.wizeline.model.ResponseDTO;
import com.wizeline.repository.UserRepository;
import com.wizeline.repository.UserRespositoryImpl;
import com.wizeline.utils.Utils;

@Service
public class UserServiceImpl implements UserService{
	
	private static final Logger log = Logger.getLogger(UserServiceImpl.class.getName());

	public ResponseDTO createUser(String user, String password) {
		
		log.info("Inicia procesamiento en capa de negocio");
		ResponseDTO response = new ResponseDTO();
		String result = "fail";
		
		if(Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
			UserRepository userDao = new UserRespositoryImpl();
			result = userDao.createUser(user, password);
			response.setCodigo("OK000");
			response.setStatus(result);
		}else {
			response.setCodigo("OK000");
			response.setStatus(result);
			response.setErrors(new ErrorDTO("ER001","Error al crear el usuario"));
		}
		
		return response;
	}

	public ResponseDTO login(String user, String password) {
		log.info("Inicia procesamiento en capa de negocio");
		ResponseDTO response = new ResponseDTO();
		String result = "";
		if(Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
			UserRepository userDao = new UserRespositoryImpl();
			result = userDao.login(user, password);
		}
		if("success".equals(result)) {
			response.setCodigo("OK000");
			response.setStatus(result);
		}else {
			response.setCodigo("ER001");
			response.setStatus("fail");
			response.setErrors(new ErrorDTO("ER001",result));
		}
		
		return response;
	}

}
