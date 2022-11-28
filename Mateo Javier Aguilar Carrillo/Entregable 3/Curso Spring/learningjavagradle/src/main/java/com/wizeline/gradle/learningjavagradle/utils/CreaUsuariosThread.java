package com.wizeline.gradle.learningjavagradle.utils;

import java.util.List;

import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import com.wizeline.gradle.learningjavagradle.repository.UserRepository;
import com.wizeline.gradle.learningjavagradle.repository.UserRepositoryImpl;
import com.wizeline.gradle.learningjavagradle.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

public class CreaUsuariosThread extends Thread {
	
	private List<UserDTO> listGlobal;
	@Autowired
	private UserServiceImpl serviceGlobal;

	public CreaUsuariosThread(List<UserDTO> userDTOList, UserServiceImpl service) {
		this.listGlobal = userDTOList;
		this.serviceGlobal = service;
	}

	private void createUsers() {
		listGlobal.stream().forEach(userDTO -> {
			String user = userDTO.getUser();
			String pass = userDTO.getPassword();
			serviceGlobal.createUser(user, pass);
		});
	}

	@Override
	public void run() {
		createUsers();
	}

}
