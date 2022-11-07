package com.wizeline.gradle.learningjavagradle.repository;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import com.wizeline.gradle.learningjavagradle.utils.exceptions.ExcepcionGenerica;

@Repository
public class UserRepositoryImpl implements UserRepository{
	private static final Logger LOGGER = Logger.getLogger(UserRepositoryImpl.class.getName());
	
	@Autowired
	MongoTemplate template;

	@Override
	public String createUser(String user, String password) {
		
		UserDTO userDTO = new UserDTO();
		userDTO.setUser(user);
		userDTO.setPassword(password);
		
		template.save(userDTO);

		LOGGER.info("Alta exitosa");
		return "success";
	}

	@Override
	public String login(String user, String password) {
		
		Optional<UserDTO> userOptional = buscarUsuario(user);
		
		if(!userOptional.isPresent()) {
			throw new ExcepcionGenerica(user);
		}
		
		return "Sesion Iniciada";
	}


	
	@Override
	public String updateUser(String user, String newPassword) {
		Update update = new Update();
		update.set("password", newPassword);
		Query query = Query.query(Criteria.where("user").is(user));
		UpdateResult updateResult = template.updateFirst(query, update, UserDTO.class);
		return updateResult.getModifiedCount() > 0 ? "Usuario Actualizado" : "Error al actualizar";
	}

	@Override
	public String deleteUser(String user) {
		Query query = Query.query(Criteria.where("user").is(user));
		DeleteResult result = template.remove(query, UserDTO.class);
		return result.getDeletedCount() > 0 ? "Usuario eliminado" : "No se realizó ninguna eliminación";
	}
	
	private Optional<UserDTO> buscarUsuario(String user){
		Query query = Query.query(Criteria.where("user").is(user));
		UserDTO userDTO = template.findOne(query, UserDTO.class);
		return Optional.ofNullable(userDTO);
		
	}
}
