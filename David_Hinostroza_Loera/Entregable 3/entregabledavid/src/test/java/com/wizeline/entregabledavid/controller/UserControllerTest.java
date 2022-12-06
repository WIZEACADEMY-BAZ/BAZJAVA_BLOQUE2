package com.wizeline.entregabledavid.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.entregabledavid.iterator.UserDTOCollectionImpl;
import com.wizeline.entregabledavid.model.ErrorDTO;
import com.wizeline.entregabledavid.model.ResponseDTO;
import com.wizeline.entregabledavid.model.UserDTO;
import com.wizeline.entregabledavid.service.UserService;
import com.wizeline.entregabledavid.utils.CommonServices;

import java.net.URI;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Disabled;

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

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @MockBean
    private CommonServices commonServices;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;


    /**
     * Metodo Test: {@link UserController#createUserAccount(UserDTO)}
     */
    @Test
    void testCreateUserAccount() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode("Code");
        responseDTO.setErrors(new ErrorDTO("An error occurred", "Not all who wander are lost"));
        responseDTO.setStatus("Status");
        when(userService.createUser((String) any(), (String) any())).thenReturn(responseDTO);

        UserDTO userDTO = new UserDTO();
        userDTO.setPassword("password");
        userDTO.setUser("User");
        String content = (new ObjectMapper()).writeValueAsString(userDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/createUser")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json; charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"status\":\"Status\",\"code\":\"Code\",\"errors\":{\"errorCode\":\"An error occurred\",\"message\":\"Not all who"
                                        + " wander are lost\"}}"));
    }

    /**
     * Metodo Test: {@link UserController#createUsersAccount(List)}
     */
    @Test
    void testCreateUsersAccount() throws Exception {
        when(userService.populateUsers()).thenReturn(new UserDTOCollectionImpl());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/createUsers")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new ArrayList<>()));
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json; charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Metodo Test: {@link UserController#createUsersAccount(List)}
     */
    @Test
    void testCreateUsersAccount2() throws Exception {
        UserDTOCollectionImpl userDTOCollectionImpl = new UserDTOCollectionImpl();
        userDTOCollectionImpl.addUser(new UserDTO("user", "password"));
        when(userService.populateUsers()).thenReturn(userDTOCollectionImpl);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/createUsers")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new ArrayList<>()));
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json; charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Metodo Test: {@link UserController#createUsersAccount(List)}
     */
    @Test
    void testCreateUsersAccount3() throws Exception {
        UserDTOCollectionImpl userDTOCollectionImpl = new UserDTOCollectionImpl();
        userDTOCollectionImpl.addUser(new UserDTO("user", "password"));
        userDTOCollectionImpl.addUser(new UserDTO("user", "password"));
        when(userService.populateUsers()).thenReturn(userDTOCollectionImpl);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/api/createUsers")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new ArrayList<>()));
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json; charset=UTF-8"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Metodo Test: {@link UserController#loginUser(String, String)}
     */
    @Test
    void testLoginUser() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode("Code");
        responseDTO.setErrors(new ErrorDTO("An error occurred", "Not all who wander are lost"));
        responseDTO.setStatus("Status");
        when(commonServices.login((String) any(), (String) any())).thenReturn(responseDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/login")
                .param("password", "values")
                .param("user", "values");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    /**
     * Metodo Test: {@link UserController#loginUser(String, String)}
     */
    @Test
    void testLoginUser2() throws Exception {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode("&");
        responseDTO.setErrors(new ErrorDTO("An error occurred", "Not all who wander are lost"));
        responseDTO.setStatus("Status");
        when(commonServices.login((String) any(), (String) any())).thenReturn(responseDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/login")
                .param("password", "values")
                .param("user", "values");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }
}

