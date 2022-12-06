package com.wizeline.baz.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;

import com.wizeline.baz.LearningSpringBootApplication;
import com.wizeline.baz.enums.UserRole;
import com.wizeline.baz.model.UserDTO;

@AutoConfigureDataMongo
@ActiveProfiles({"test"})
@SpringBootTest(classes = LearningSpringBootApplication.class)
class UserRepositoryImplTest {
	
	@Autowired
	MongoTemplate mongo;
	
	@Autowired
	private UserRepository repository;
		
	@BeforeEach
	void beforeEach() {
		mongo.remove(new Query(), UserDTO.class);
	}
	
	@Test
	void createUser() {
		String userId = UUID.randomUUID().toString();
		repository.createUser(userId, "Abraham", "email@gmai.com", "password", Arrays.asList(UserRole.USER));
		long count = mongo.count(new Query(), UserDTO.class);
		assertEquals(1L, count);
		UserDTO savedUser = mongo.findOne(Query.query(Criteria.where("_id").is(userId)), UserDTO.class);
		assertNotNull(savedUser);
		assertNotNull(savedUser.getEmail());
		assertNotNull(savedUser.getEmail());
		assertNotNull(savedUser.getPassword());
		assertNotNull(savedUser.getRoles());
		assertNotNull(savedUser.getCreationTime());
		assertNull(savedUser.getUpdateTime());
	}
	
	@Test
	void updateSuccessUserPassword() {
		String userId = UUID.randomUUID().toString();
		repository.createUser(userId, "Abraham", "email@gmai.com", "password", Arrays.asList(UserRole.USER));
		String newPassword = "mynewpassword";
		boolean updated = repository.updateUserPassword(userId, newPassword);
		assertTrue(updated);
		UserDTO savedUser = mongo.findOne(Query.query(Criteria.where("_id").is(userId)), UserDTO.class);
		assertNotNull(savedUser);
		assertEquals(newPassword, savedUser.getPassword());
		assertNotNull(savedUser.getUpdateTime());
	}
	
	@Test
	void updateFailedUserPassword() {
		boolean updated = repository.updateUserPassword("FAKE_ID", "mynewpassword");
		assertFalse(updated);
	}
	
	
	@Test
	void existById() {
		String userId = UUID.randomUUID().toString();
		repository.createUser(userId, "Abraham", "email@gmai.com", "password", Arrays.asList(UserRole.USER));
		boolean exists = repository.existsById(userId);
		assertTrue(exists);
	}
	
	@Test
	void findUserById() {
		String userId = UUID.randomUUID().toString();
		repository.createUser(userId, "Abraham", "email@gmai.com", "password", Arrays.asList(UserRole.USER));
		Optional<UserDTO> optUser = repository.findUserById(userId);
		assertNotNull(optUser);
		assertTrue(optUser.isPresent());
		UserDTO user = optUser.get();
		assertEquals(userId, user.getId());
	}
	
	@Test
	void findUserByEmail() {
		String userId = UUID.randomUUID().toString();
		String email = "email@gmai.com";
		repository.createUser(userId, "Abraham", email, "password", Arrays.asList(UserRole.USER));
		Optional<UserDTO> optUser = repository.findUserByEmail(email);
		assertNotNull(optUser);
		assertTrue(optUser.isPresent());
		UserDTO user = optUser.get();
		assertEquals(email, user.getEmail());
	}
	
	@Test
	void existByEmail() {
		String email = "myemail@gmail.com";
		repository.createUser(UUID.randomUUID().toString(), "Abraham", email, "password", Arrays.asList(UserRole.USER));
		boolean exists = repository.existsByEmail(email);
		assertTrue(exists);
	}
}
