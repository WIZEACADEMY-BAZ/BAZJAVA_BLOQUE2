
package com.cursojava.learning2.service;

import com.cursojava.learning2.model.ResponseDTO;
import com.cursojava.learning2.model.UserDTO;

import java.util.List;

public interface UserService {

	ResponseDTO crearUsuario(UserDTO usuario);
	
	ResponseDTO login(String user, String password);

	ResponseDTO crearUsuarios(List<UserDTO> usuarios);
	
}
