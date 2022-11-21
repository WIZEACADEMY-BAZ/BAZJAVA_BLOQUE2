package com.wizeline.gradle.learningjavagradle.service;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.repository.UserRepository;
import com.wizeline.gradle.learningjavagradle.utils.Utils;

@Service
public class UserServiceImpl implements UserService{
	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
	
	@Autowired
	UserRepository userDao;

	@Override
	public ResponseDTO createUser(String user, String password) {
		LOGGER.info("Inicia procesamiento en capa de negocio");
		ResponseDTO response = new ResponseDTO();
		String result = "fail"; 
		if (Utils.validateNullValue(user)) {
			result = userDao.createUser(user, password);
			response.setCode("OK001");
			response.setStatus(result);
		}else {
			response.setCode("OK000");
			response.setStatus(result);
			response.setErrors(new ResponseDTO.ErrorDTO("ER000","Error al crear usuario"));
		}
		return response;
	}
	
	@Override
	public ResponseDTO createUser(String user) {
		LOGGER.info("Inicia procesamiento en capa de negocio");
		ResponseDTO response = new ResponseDTO();
		String result = "fail"; 
		if (Utils.validateNullValue(user)) {
			result = userDao.createUser(user);
			response.setCode("OK001");
			response.setStatus(result);
		}else {
			response.setCode("OK000");
			response.setStatus(result);
			response.setErrors(new ResponseDTO.ErrorDTO("ER000","Error al crear usuario"));
		}
		return response;
	}

	@Override
	public ResponseDTO login(String user, String password) {
		LOGGER.info("Inicia procesamiento en capa de negocio");
		ResponseDTO response = new ResponseDTO();
		String result = "fail";
		if (Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
			result = userDao.login(user, password);
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

	@Override
	public ResponseDTO updateUser(String user, String newPassword) {
		LOGGER.info("Inicia procesamiento en capa de negocio");
		ResponseDTO response = new ResponseDTO();
		String result = "fail"; 
		if (Utils.validateNullValue(user) && Utils.validateNullValue(newPassword)) {
			result = userDao.updateUser(user, newPassword);
			response.setCode("OK000");
			response.setStatus(result);
		}else {
			response.setCode("OK000");
			response.setStatus(result);
			response.setErrors(new ResponseDTO.ErrorDTO("ER003","Error al actualizar usuario"));
		}
		return response;
	}

	@Override
	public ResponseDTO deleteUser(String user) {
		LOGGER.info("Inicia procesamiento en capa de negocio");
		ResponseDTO response = new ResponseDTO();
		String result = "fail"; 
		if (Utils.validateNullValue(user)) {
			result = userDao.deleteUser(user);
			response.setCode("OK000");
			response.setStatus(result);
		}else {
			response.setCode("OK000");
			response.setStatus(result);
			response.setErrors(new ResponseDTO.ErrorDTO("ER004","Error al borrar usuario"));
		}
		return response;
	}	

}
