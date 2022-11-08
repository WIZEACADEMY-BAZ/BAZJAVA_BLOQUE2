package com.wizeline.baz.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;
import com.wizeline.baz.enums.UserRole;
import com.wizeline.baz.model.UserDTO;

@Repository
public class UserRepositoryImpl implements UserRepository  {
	
	@Autowired
	private MongoTemplate mongo;

	@Override
	public UserDTO createUser(String id, String name, String email, String password, List<UserRole> roles) {
		UserDTO user = new UserDTO();
		user.setId(id);
		user.setName(name);
		user.setEmail(email);
		user.setPassword(password);
		user.setCreationTime(LocalDateTime.now());
		user.setRoles(roles);
		mongo.insert(user);
		return user;
	}
	
	@Override
	public boolean updateUserPassword(String userId, String password) {
		Update update = new Update();
		update.set("password", password);
		update.set("updateTime", LocalDateTime.now());
		Query query = Query.query(Criteria.where("_id").is(userId));
		UpdateResult updateResult = mongo.updateFirst(query, update, UserDTO.class);
		return updateResult.getModifiedCount() > 0;
	}
	
	@Override
	public boolean existsById(String userId) {
		Query query = Query.query(Criteria.where("_id").is(userId));
		return mongo.exists(query, UserDTO.class);
	}

	@Override
	public Optional<UserDTO> findUserById(String userId) {
		Query query = Query.query(Criteria.where("_id").is(userId));
		UserDTO user = mongo.findOne(query, UserDTO.class);
		return Optional.ofNullable(user);
	}
	
	@Override
	public boolean existsByEmail(String email) {
		Query query = Query.query(Criteria.where("email").is(email));
		return mongo.exists(query, UserDTO.class);
	}
	
	@Override
	public Optional<UserDTO> findUserByEmail(String email) {
		Query query = Query.query(Criteria.where("email").is(email));
		UserDTO user = mongo.findOne(query, UserDTO.class);
		return Optional.ofNullable(user);
	}

	@Override
	public List<UserDTO> getAllUsers() {
		return mongo.findAll(UserDTO.class);
	}
}
