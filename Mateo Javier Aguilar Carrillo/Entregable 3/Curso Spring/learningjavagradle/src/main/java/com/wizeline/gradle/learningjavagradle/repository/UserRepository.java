package com.wizeline.gradle.learningjavagradle.repository;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface UserRepository {
	
	String createUser(String user, String password);
	
	String updateUser(String user, String newPassword);
	
	String deleteUser(String user);
	
	String login(String user, String password);

	String createUser(String user) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;

}
