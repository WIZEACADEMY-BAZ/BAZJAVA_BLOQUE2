
package com.cursojava.learning2.service;

import com.cursojava.learning2.model.ResponseDTO;

public interface UserService {

	ResponseDTO createUser(String user, String password);
	
	ResponseDTO login(String user, String password);
	
}
