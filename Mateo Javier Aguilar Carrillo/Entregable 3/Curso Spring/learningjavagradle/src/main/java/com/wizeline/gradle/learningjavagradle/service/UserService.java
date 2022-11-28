package com.wizeline.gradle.learningjavagradle.service;

import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface UserService {

	ResponseDTO createUser(String user, String password);
	
	ResponseDTO createUser(String user) throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException;
	
	ResponseDTO updateUser(String user, String newPassword);
	
	ResponseDTO deleteUser(String user);
	
	ResponseDTO login(String user, String password);
}
