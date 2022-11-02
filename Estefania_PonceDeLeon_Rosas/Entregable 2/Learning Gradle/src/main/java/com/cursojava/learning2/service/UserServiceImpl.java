/**
 * 
 */

package com.cursojava.learning2.service;

import java.util.List;
import java.util.logging.Logger;

import com.cursojava.learning2.model.ErrorDTO;
import com.cursojava.learning2.model.ResponseDTO;
import com.cursojava.learning2.model.UserDTO;
import com.cursojava.learning2.repository.UserRepository;
import com.cursojava.learning2.repository.UserRepositoryImpl;
import com.cursojava.learning2.utils.Utils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * @author jonathan.torres
 *
 */

public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());

	private static String responseTextThread = "";

	private static final String SUCCESS_CODE = "OK000";
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public ResponseDTO crearUsuario(UserDTO usuario) {
		LOGGER.info("Inicia procesamiento en capa de negocio");
		ResponseDTO response = new ResponseDTO();
		String result = "";
		if (Utils.validateNullValue(usuario.getUser()) && Utils.validateNullValue(usuario.getPassword())) {
			result = this.userRepository.crearUsuario(usuario);
			response.setCode(SUCCESS_CODE);
			response.setStatus(result);
		}else {
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
			UserRepository userDao = this.userRepository;
			result = userDao.login(user, password);
		}
		if("success".equals(result)) {
			response.setCode(SUCCESS_CODE);
			response.setStatus(result);
		} else {
			response.setCode(SUCCESS_CODE);
			response.setErrors(new ErrorDTO("ER001",result));
			response.setStatus("fail");
		}
		return response;
	}

	@Override
	public ResponseDTO crearUsuarios(List<UserDTO> usuarios) {
		ResponseDTO response=new ResponseDTO();
		for( UserDTO user: usuarios){
			response=this.crearUsuario(user);
		}
		return response;
	}

}
