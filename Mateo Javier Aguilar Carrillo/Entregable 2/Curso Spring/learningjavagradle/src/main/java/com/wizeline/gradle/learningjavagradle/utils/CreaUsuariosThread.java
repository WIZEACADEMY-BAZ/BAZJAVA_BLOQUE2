package com.wizeline.gradle.learningjavagradle.utils;

import java.util.List;

import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import com.wizeline.gradle.learningjavagradle.repository.UserRepository; 

public class CreaUsuariosThread extends Thread {
	
	private List<UserDTO> listGlobal;
	private UserRepository repositoryGlobal;

	public CreaUsuariosThread(List<UserDTO> userDTOList, UserRepository repository) {
		this.listGlobal = userDTOList;
		this.repositoryGlobal = repository;
	}

	private void createUsers() {
		listGlobal.stream().forEach(userDTO -> {
			String user = userDTO.getUser();
			String pass = userDTO.getPassword();
			repositoryGlobal.createUser(user, pass);
		});
	}

	@Override
	public void run() {
		createUsers();
	}

}
