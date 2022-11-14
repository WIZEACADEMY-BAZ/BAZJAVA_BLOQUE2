package com.wizeline.gradle.learningjavagradle.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.wizeline.gradle.learningjavagradle.model.ErrorDTO;
import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import com.wizeline.gradle.learningjavagradle.repository.UserRepository;
import com.wizeline.gradle.learningjavagradle.repository.UserRepositoryImpl;
import com.wizeline.gradle.learningjavagradle.utils.Utils;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
	
	@Override
	public ResponseDTO createUser(String user, String password) {
		LOGGER.info("Inicia procesamiento en capa de negocio");
		ResponseDTO response = new ResponseDTO();
		String result = "fail"; 
		if (Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
			UserRepository userDao = new UserRepositoryImpl();
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
			UserRepository userDao = new UserRepositoryImpl();
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
	public ResponseDTO crearUsuarios(List<UserDTO> usuarios) {
		ResponseDTO response=new ResponseDTO();
		for( UserDTO user: usuarios){
			response=this.createUser(user.getUser(),user.getPassword());
		}
		return response;
	}
}
