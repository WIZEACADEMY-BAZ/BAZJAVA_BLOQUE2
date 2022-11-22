package com.wizeline.learningjavamaven.controller;

import com.wizeline.learningjavamaven.model.ResponseDTO;
import com.wizeline.learningjavamaven.model.UserDTO;
import com.wizeline.learningjavamaven.model.UserDateDTO;
import com.wizeline.learningjavamaven.service.UserService;
import com.wizeline.learningjavamaven.utils.CommonServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

  @InjectMocks
  UserController userController;

  @Mock
  UserService userService;

  @Mock
  CommonServices commonServices;

  ResponseDTO responseDTO = new ResponseDTO();

  @BeforeEach
  void before() {
    responseDTO.setStatus("correcto");
    responseDTO.setCode("OK000");
  }

  @Test
  @DisplayName("Procesos de consumo de usuarios")
  void getUsers() {
    ResponseEntity<String> response = userController.getUsers();
    assertNotNull(response);
  }

  @Test
  @DisplayName("Logueo usuario")
  void loginUser() {
    when(commonServices.login(Mockito.any(), Mockito.anyString())).thenReturn(responseDTO);
    ResponseEntity<ResponseDTO> response = userController.loginUser("alex", "pass");
    assertNotNull(response);
  }

  @Test
  @DisplayName("Creacion cuenta")
  void createUserAccount() {
    UserDTO userDTO = new UserDTO();
    userDTO.setId("1");
    userDTO.setUsername("user");
    userDTO.setPassword("pass");
    ResponseDTO responseDTO = new ResponseDTO();
    responseDTO.setStatus("200");
    responseDTO.setCode("200");
    when(userService.createUser(Mockito.anyString(), Mockito.anyString())).thenReturn(responseDTO);
    ResponseEntity<ResponseDTO> response = userController.createUserAccount(userDTO);
    assertEquals("200", response.getBody().getStatus());
  }

  @Test
  @DisplayName("Creacion cuentas")
  void createUsersAccount() {
    UserDTO userDTO = new UserDTO();
    userDTO.setId("1");
    userDTO.setUsername("user");
    userDTO.setPassword("pass");
    UserDTO userDTODos = new UserDTO();
    userDTODos.setId("2");
    userDTODos.setUsername("alex");
    userDTODos.setPassword("pass");
    List<UserDTO> users = Arrays.asList(userDTO, userDTODos);
    ResponseDTO responseDTO = new ResponseDTO();
    responseDTO.setStatus("200");
    responseDTO.setCode("200");
    when(userService.createUser(Mockito.anyString(), Mockito.anyString())).thenReturn(responseDTO);
    ResponseEntity<List<ResponseDTO>> response = userController.createUsersAccount(users);
    assertTrue(response.getBody().size() > 0);
  }

  @Test
  @DisplayName("Actualizacion cuenta")
  void updateUserAccount() {
    UserDTO userDTO = new UserDTO();
    userDTO.setId("1");
    userDTO.setUsername("user");
    userDTO.setPassword("pass");
    ResponseDTO responseDTO = new ResponseDTO();
    responseDTO.setStatus("200");
    responseDTO.setCode("200");
    when(userService.updateUser(Mockito.anyString(), Mockito.anyString())).thenReturn(responseDTO);
    ResponseEntity<ResponseDTO> response = userController.updateUserAccount(userDTO);
    assertEquals("200", response.getBody().getStatus());
  }

  @Test
  @DisplayName("Eliminacion cuenta")
  void deleteUserAccount() {
    String username = "alex";
    ResponseDTO responseDTO = new ResponseDTO();
    responseDTO.setStatus("200");
    responseDTO.setCode("200");
    when(userService.deleteUser(Mockito.anyString())).thenReturn(responseDTO);
    ResponseEntity<ResponseDTO> response = userController.deleteUserAccount(username);
    assertEquals("200", response.getBody().getStatus());
  }

  @Test
  void userDate() {
    UserDateDTO userDateDTO = new UserDateDTO();
    userDateDTO.setUser("1");
    userDateDTO.setAnio("12");
    userDateDTO.setFechaNacimiento("12-01-2010");
    List<UserDateDTO> userDateDTOList = Collections.singletonList(userDateDTO);
    when(userService.getUserDateIterator("1")).thenReturn(userDateDTOList);
    ResponseEntity<List<UserDateDTO>> response = userController.userDate("1");
    assertNotNull(response);
  }
}