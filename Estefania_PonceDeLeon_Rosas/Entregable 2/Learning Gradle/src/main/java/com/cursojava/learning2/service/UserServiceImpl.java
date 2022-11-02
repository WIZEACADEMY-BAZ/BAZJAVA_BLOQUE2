/**
 * 
 */

package com.cursojava.learning2.service;

import java.util.logging.Logger;

import com.cursojava.learning2.model.ErrorDTO;
import com.cursojava.learning2.model.ResponseDTO;
import com.cursojava.learning2.repository.UserRepository;
import com.cursojava.learning2.repository.UserRepositoryImpl;
import com.cursojava.learning2.utils.Utils;
import org.springframework.context.annotation.Bean;

/**
 * @author jonathan.torres
 *
 */

public class UserServiceImpl implements UserService {

	private static final Logger LOGGER = Logger.getLogger(UserServiceImpl.class.getName());
	
	
	@Bean
    public static UserRepository userRepository() {
        return new UserRepositoryImpl();
    }
	
	@Override
	public ResponseDTO createUser(String user, String password) {
		LOGGER.info("Inicia procesamiento en capa de negocio");
		ResponseDTO response = new ResponseDTO();
		String result = "fail"; 
		if (Utils.validateNullValue(user) && Utils.validateNullValue(password)) {
			UserRepository userDao = userRepository();
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
			UserRepository userDao = userRepository();
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

}
