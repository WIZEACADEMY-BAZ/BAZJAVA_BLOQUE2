/**
 * 
 */
package com.cursojava.learning2.repository;

import com.cursojava.learning2.model.UserDTO;

/**
 * @author Wizeline
 *
 */
public interface UserRepository {

	String crearUsuario(UserDTO usuario);
	
	String login(String user, String password);
	
}
