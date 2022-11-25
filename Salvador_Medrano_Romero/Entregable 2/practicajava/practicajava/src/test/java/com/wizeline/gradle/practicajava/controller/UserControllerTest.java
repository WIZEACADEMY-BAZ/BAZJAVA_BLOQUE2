package com.wizeline.gradle.practicajava.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.gradle.practicajava.model.ErrorDTO;
import com.wizeline.gradle.practicajava.model.ResponseDTO;
import com.wizeline.gradle.practicajava.model.UserDTO;
import com.wizeline.gradle.practicajava.service.UserServiceImpl;

@ContextConfiguration(classes = { UserController.class })
@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserControllerTest {

	@Autowired
	private UserController userController;

	@MockBean
	private UserServiceImpl userService;
	
	@Mock
	private RestTemplate restTemplate;

	@Test
	void loginUserTest() throws Exception {
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setCode("Codigo");
		responseDTO.setErrors(new ErrorDTO("Error", "Error"));
		responseDTO.setStatus("Estatus");
		when(userService.login((String) any(), (String) any())).thenReturn(responseDTO);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/login")
				.param("password", "values").param("user", "values");
		MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void createUserAccountTest() throws Exception {
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setCode("Codigo");
		responseDTO.setErrors(new ErrorDTO("Error", "Error"));
		responseDTO.setStatus("Status");
		when(userService.createUser((String) any(), (String) any())).thenReturn(responseDTO);

		UserDTO userDTO = new UserDTO();
		userDTO.setPassword("Pass");
		userDTO.setUser("Usuario");
		String content = (new ObjectMapper()).writeValueAsString(userDTO);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/createUser")
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

}
