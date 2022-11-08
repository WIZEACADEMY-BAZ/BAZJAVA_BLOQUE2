package com.wizeline.baz.repository;

import java.util.List;
import java.util.Optional;

import com.wizeline.baz.enums.UserRole;
import com.wizeline.baz.model.UserDTO;

public interface UserRepository {
	UserDTO createUser(String id, String name, String email, String password, List<UserRole> roles);
	boolean updateUserPassword(String userId, String password);
	boolean existsById(String userId);
	boolean existsByEmail(String email);
	Optional<UserDTO> findUserById(String userId);
	Optional<UserDTO> findUserByEmail(String email);
	List<UserDTO> getAllUsers();
}
