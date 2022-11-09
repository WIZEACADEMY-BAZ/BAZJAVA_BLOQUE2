package com.wizeline.gradle.learningjavagradle.repository;

public interface UserRepository {
	
	String createUser(String user, String password);
	
	String updateUser(String user, String newPassword);
	
	String deleteUser(String user);
	
	String login(String user, String password);

	String createUser(String user);

}
