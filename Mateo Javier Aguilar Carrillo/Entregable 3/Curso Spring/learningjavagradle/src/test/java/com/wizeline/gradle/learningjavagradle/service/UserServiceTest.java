package com.wizeline.gradle.learningjavagradle.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import com.wizeline.gradle.learningjavagradle.repository.UserRepositoryImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceTest.class);

	@InjectMocks
	UserServiceImpl userServiceImpl;

	@Mock
	UserRepositoryImpl userRepository;

	private static final String USER = "mateo";
	private static final String PASSWORD = "Mateojavier1@";
	private static final String NEW_PASSWORD = "Mateojavier2@";

	@Test
	public void createUserWithPasswordTest() {
		LOGGER.info("createUserWithPassword Testing...");

		String response = "";

		when(userRepository.createUser(USER,PASSWORD)).thenReturn(response);

		assertNotNull(userServiceImpl.createUser(USER, PASSWORD));
		assertNotNull(response);
	}

	@Test
	public void createUserWithRandomPasswordTest()
			throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
		LOGGER.info("createUserWithPassword Testing...");
		String response = "";

		lenient().when(userRepository.createUser(USER)).thenReturn(response);

		assertNotNull(userServiceImpl.createUser(USER));
		assertNotNull(response);

	}

	@Test
	public void createUserWithPasswordErrorTest() {
		LOGGER.info("createUserWithPasswordError Testing...");
		String response = "";

		lenient().when(userRepository.createUser(null, PASSWORD)).thenReturn(response);

		assertNotNull(userServiceImpl.createUser(null, PASSWORD));
		assertEquals(userServiceImpl.createUser(null, PASSWORD).getCode(), "ER001");
	}

	@Test
	public void createUserWithoutPasswordErrorTest()
			throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
		LOGGER.info("createUserWithPasswordError Testing...");
		String response = "";

		lenient().when(userRepository.createUser(null)).thenReturn(response);

		assertNotNull(userServiceImpl.createUser(null));
		assertEquals(userServiceImpl.createUser(null).getCode(), "ER001");
	}

	@Test
	public void loginTest() {
		LOGGER.info("login Testing...");
		String response = "";
		userRepository.createUser(USER,PASSWORD);
		when(userRepository.login(USER,PASSWORD)).thenReturn(response);

		assertNotNull(userServiceImpl.login(USER, PASSWORD));
		assertEquals(userServiceImpl.login(USER,PASSWORD).getCode(), "OK000");
	}

	@Test
	public void failedLoginTest() {
		LOGGER.info("failedLogin Testing...");
		String response = "";
		userRepository.createUser(USER,PASSWORD);
		lenient().when(userRepository.login(USER,null)).thenReturn(response);

		assertNotNull(userServiceImpl.login(USER, null));
		assertEquals(userServiceImpl.login(USER,null).getCode(), "ER001");
	}

	@Test
	public void updateUserTest() {
		LOGGER.info("updateUser Testing...");
		String response = "";
		userRepository.createUser(USER, PASSWORD);
		when(userRepository.updateUser(USER, NEW_PASSWORD)).thenReturn(response);

		assertNotNull(userServiceImpl.updateUser(USER, NEW_PASSWORD));
		assertEquals(userServiceImpl.updateUser(USER, NEW_PASSWORD).getCode(), "OK000");
	}

	@Test
	public void updateUserErrorTest() {
		LOGGER.info("updateUserError Testing...");
		String response = "";
		userRepository.createUser(USER, PASSWORD);
		lenient().when(userRepository.updateUser(USER, null)).thenReturn(response);

		assertNotNull(userServiceImpl.updateUser(USER, null));
		assertEquals(userServiceImpl.updateUser(USER, null).getCode(), "ER001");
	}

	@Test
	public void deleteUserTest() {
		LOGGER.info("deleteUser Testing...");
		String response = "";
		userRepository.createUser(USER, PASSWORD);
		when(userRepository.deleteUser(USER)).thenReturn(response);

		assertNotNull(userServiceImpl.deleteUser(USER));
		assertEquals(userServiceImpl.deleteUser(USER).getCode(), "OK000");
	}

	@Test
	public void deleteUserErrorTest() {
		LOGGER.info("deleteUserError Testing...");
		String response = "";
		userRepository.createUser(USER, PASSWORD);
		lenient().when(userRepository.deleteUser(null)).thenReturn(response);

		assertNotNull(userServiceImpl.deleteUser(null));
		assertEquals(userServiceImpl.deleteUser(null).getCode(), "ER001");
	}
}
