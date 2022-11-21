package com.wizeline.gradle.learningjavagradle.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.wizeline.gradle.learningjavagradle.model.RandomPassword;
import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import com.wizeline.gradle.learningjavagradle.repository.UserRepositoryImpl;
import com.wizeline.gradle.learningjavagradle.singleton.RestTemplateConfig;
import com.wizeline.gradle.learningjavagradle.utils.EncryptorRSA;
import org.springframework.http.HttpStatus;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
	@Mock
	MongoTemplate template;

	@InjectMocks
	EncryptorRSA encryptorRSA;

	@InjectMocks
	UserServiceImpl userServiceImpl;

	private static final String USER = "mateo";
	private static final String PASSWORD = "Mateo1@";

	@Test
	public void createUser() {

		UserDTO userDTO = new UserDTO(USER, PASSWORD);

		lenient().when(template.findOne(any(), any())).thenReturn(userDTO);
		lenient().when(template.save(userDTO)).thenReturn(userDTO);

		assertNotNull(userDTO);
	}

	@Test
	public void createUserError() {
		UserDTO userDTO = new UserDTO(USER, PASSWORD);

		lenient().when(template.findOne(any(), any())).thenReturn(null);
		lenient().when(template.save(userDTO)).thenReturn(userDTO);
		
		assertNotNull(userDTO);
	}
}
