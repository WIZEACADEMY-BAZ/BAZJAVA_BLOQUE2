package com.wizeline.gradle.learningjavagradle.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import com.wizeline.gradle.learningjavagradle.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.utils.Utils;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

@Service
public class UserServiceImpl implements UserService{
	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

	@Autowired
	UserRepositoryImpl userDao;

	@Override
	public ResponseDTO createUser(String user, String password) {
		LOGGER.info("Inicia procesamiento en capa de negocio");
		ResponseDTO response = new ResponseDTO();
		String result = "fail"; 
		if (Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
			result = userDao.createUser(user, password);
			response.setCode("OK000");
			response.setStatus(result);
		}else {
			response.setCode("ER001");
			response.setStatus(result);
			response.setErrors(new ResponseDTO.ErrorDTO("ER000","Error al crear usuario"));
		}
		return response;
	}
	
	@Override
	public ResponseDTO createUser(String user) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
		LOGGER.info("Inicia procesamiento en capa de negocio");
		ResponseDTO response = new ResponseDTO();
		String result = "fail";
		if (Utils.validateNullValue(user)) {
			result = userDao.createUser(user);
			response.setCode("OK000");
			response.setStatus(result);
		}else {
			response.setCode("ER001");
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
			response.setStatus("success");
		}else {
			response.setCode("ER001");
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
			response.setCode("ER001");
			response.setStatus(result);
			response.setErrors(new ResponseDTO.ErrorDTO("ER004","Error al borrar usuario"));
		}
		return response;
	}	

}
