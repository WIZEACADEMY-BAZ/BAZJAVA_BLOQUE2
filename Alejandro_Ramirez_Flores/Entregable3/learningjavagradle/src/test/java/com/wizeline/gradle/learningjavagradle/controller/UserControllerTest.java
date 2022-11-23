package com.wizeline.gradle.learningjavagradle.controller;

import com.wizeline.gradle.learningjavagradle.enums.AccountType;
import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;
import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import com.wizeline.gradle.learningjavagradle.service.BankAccountService;
import com.wizeline.gradle.learningjavagradle.service.UserService;
import com.wizeline.gradle.learningjavagradle.utils.CommonServices;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.stubbing.BaseStubbing;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static com.wizeline.gradle.learningjavagradle.enums.AccountType.AHORRO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.ExpectedCount.once;


@ExtendWith(MockitoExtension.class)
class UserControllerTest {
	private static final Logger LOGGER = Logger.getLogger(UserControllerTest.class.getName());
	
	    @Mock
	    private RestTemplate restTemplate;
	    @Mock
	    private KafkaProducer producer;
	    @Mock
	    private UserService userService;
	    @Mock
	    private BankAccountService bankAccountService;
	    @Mock
	    private CommonServices commonServices;

	    @InjectMocks
	    private UserController userController;

	    @Mock
	    public UserDTO userDTO;

	@Test
	void testGetUsers() {
		LOGGER.info("Realizando Prueba del metodo getUsers()");
		ResponseEntity<?> createUser = userController.getUsers();
		 assertNotNull(createUser);
        assertTrue(createUser.getStatusCode().is2xxSuccessful(), "Respuesta Exitosa");
	}

	@Test
	void testLogin() {
        LOGGER.info("Realizando Prueba");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode("200");
        String user = "user1@wizeline.com";
        String password = "Pass1$";
        LOGGER.info("Condiciones necesarias a cumplir en la Prueba");
        when(commonServices.login(user, password)).thenReturn(responseDTO);
        ResponseEntity<ResponseDTO> loginResponse = userController.login(user, password);
        LOGGER.info("Verificar Salida del metodo.");
        assertTrue(loginResponse.getStatusCode().is2xxSuccessful(), "El código HTTP No EXITOSO");
        assertTrue(loginResponse.hasBody(), "Response body no presente en respuesta");
        assertEquals("200", loginResponse.getBody().getCode(), "Código de __Respuesta para DTO no es 200");
        verify(commonServices, times(1)).login(user, password);
	}

	@Test
	void testCreateUserAccount() {
		  LOGGER.info("Preparando prueba del metodo createUserAccount()");
	        //Preparo el esenario para la prueba
	        UserDTO userDTO = new UserDTO();
	        userDTO.setUsers("alex");
	        userDTO.setPassword("567");
	        ResponseDTO responseDTO = new ResponseDTO();
	        responseDTO.setCode("201");
	        LOGGER.info("Condiciones a cumplir para realizar Pruebas");
	        when(userService.createUser(userDTO.getUsers(), userDTO.getPassword())).thenReturn(responseDTO);
	        ResponseEntity<ResponseDTO> createUser = userController.createUserAccount(userDTO);
	        LOGGER.info("Validando Pruebas");
	        assertTrue(createUser.getStatusCode().is2xxSuccessful(), "El codigo de respuesta fue exitoso");
	        assertTrue(createUser.hasBody(), "Response body no presente en la respuesta. ");
	        assertEquals("201", createUser.getBody().getCode(), "Codigo respuesta 201");
	}

	@Test
	void testCreateUsersAccount() {
		LOGGER.info("realizando la Prueba");
        List<UserDTO> responseList = new ArrayList<>();

        ResponseEntity respuesta = userController.createUsersAccount(responseList);

        assertTrue(respuesta.getStatusCode().is2xxSuccessful(), "Codigo de Respuesta Exitoso");
	}
}
