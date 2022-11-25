package com.wizeline.gradle.practicajava.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.gradle.practicajava.model.ErrorDTO;
import com.wizeline.gradle.practicajava.model.ResponseDTO;
import com.wizeline.gradle.practicajava.model.UserDTO;
import com.wizeline.gradle.practicajava.service.UserService;

@ContextConfiguration(classes = { UserController.class })
@ExtendWith(SpringExtension.class)
class UserControllerTest {

	@Autowired
	private UserController userController;

	@MockBean
	private UserService userService;

	/**
	 * Metodo Test: {@link UserController#loginUser(String, String)}
	 */
	@Test
	void testLoginUser() throws Exception {
		ResponseDTO responseDTO = new ResponseDTO();
		responseDTO.setCode("Codigo");
		responseDTO.setErrors(new ErrorDTO("Error", "Error"));
		responseDTO.setStatus("Estatus");
		when(userService.login((String) any(), (String) any())).thenReturn(responseDTO);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/login")
				.param("password", "values").param("user", "values");
		ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build()
				.perform(requestBuilder);
		actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
	}

	/**
	 * Metodo Test: {@link UserController#createUser(UserDTO)}
	 */
	@Test
	void testCreateUserAccount() throws Exception {
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
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string(
						"{\"status\":\"Status\",\"code\":\"Code\",\"errors\":{\"errorCode\":\"An error occurred\",\"message\":\"Not all who"
								+ " wander are lost\"}}"));
	}

}
