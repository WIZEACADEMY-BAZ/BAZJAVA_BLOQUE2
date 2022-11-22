package com.wizeline.learningjavamaven.service;

import com.wizeline.learningjavamaven.model.ResponseDTO;
import com.wizeline.learningjavamaven.model.UserDTO;
import com.wizeline.learningjavamaven.model.UserDateDTO;
import com.wizeline.learningjavamaven.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @InjectMocks
  UserServiceImpl userService;

  @Mock
  MongoTemplate mongoTemplate;

  @Mock
  UserRepository userRepository;

  @Test
  @DisplayName("Creacion usuario")
  void createUser() {
    UserDTO userDTO = new UserDTO();
    userDTO.setUsername("alex");
    userDTO.setPassword("pass");
    userDTO.setId("12");
    when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(userDTO);
    ResponseDTO response = userService.createUser("alex", "pass");
    assertNotNull(response);
  }

  @Test
  @DisplayName("Creacion usuario no existente")
  void createUserNotExist() {
    UserDTO userDTO = new UserDTO();
    userDTO.setUsername("alex");
    userDTO.setPassword("pass");
    userDTO.setId("12");
    when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(null);
    when(mongoTemplate.save(Mockito.any())).thenReturn(userDTO);
    ResponseDTO response = userService.createUser("alex", "pass");
    assertNotNull(response);
  }

  @Test
  @DisplayName("Creacion usuario error no usuario")
  void createUserNotUser() {
    ResponseDTO response = userService.createUser(null, "pass");
    assertNotNull(response);
  }

  @Test
  @DisplayName("Creacion usuario error no contraseña")
  void createUserNotPass() {
    ResponseDTO response = userService.createUser("alex", null);
    assertNotNull(response);
  }

  @Test
  @DisplayName("Logueo usuario")
  void login() {
    UserDTO userDTO = new UserDTO();
    userDTO.setUsername("alex");
    userDTO.setPassword("pass");
    userDTO.setId("12");
    when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(userDTO);
    ResponseDTO response = userService.login("alex", "pass");
    assertNotNull(response);
  }

  @Test
  @DisplayName("Logueo usuario error nulo")
  void loginUserNull() {
    when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(null);
    ResponseDTO response = userService.login("alex", "pass");
    assertNotNull(response);
  }

  @Test
  @DisplayName("Logueo usuario nulo")
  void loginNotUser() {
    ResponseDTO response = userService.login(null, "pass");
    assertNotNull(response);
  }

  @Test
  @DisplayName("Logueo usuario contraseña nulo")
  void loginNotPass() {
    ResponseDTO response = userService.login("alex", null);
    assertNotNull(response);
  }

  @Test
  @DisplayName("Actualizacion usuario")
  void updateUser() {
    UserDTO userDTO = new UserDTO();
    userDTO.setUsername("alex");
    userDTO.setPassword("pass");
    userDTO.setId("12");
    when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(userDTO);
    ResponseDTO response = userService.updateUser("alex", "pass");
    assertNotNull(response);
  }

  @Test
  @DisplayName("Actualizacion usuario error nulo")
  void updateUserNull() {
    when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(null);
    ResponseDTO response = userService.updateUser("alex", "pass");
    assertNotNull(response);
  }

  @Test
  @DisplayName("Eliminacion usuario")
  void deleteUser() {
    UserDTO userDTO = new UserDTO();
    userDTO.setUsername("alex");
    userDTO.setPassword("pass");
    userDTO.setId("12");
    Optional<UserDTO> optionalUserDTO = Optional.of(userDTO);
    when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(userDTO);
    when(userRepository.findById(Mockito.any())).thenReturn(optionalUserDTO);
    doNothing().when(userRepository).delete(userDTO);
    ResponseDTO response = userService.deleteUser("alex");
    assertNotNull(response);
  }

  @Test
  @DisplayName("Eliminacion usuario nulo")
  void deleteUserNull() {
    when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(null);
    ResponseDTO response = userService.deleteUser("alex");
    assertNotNull(response);
  }

  @Test
  @DisplayName("Eliminacion usuario nulo opcional")
  void deleteUserNullOptional() {
    UserDTO userDTO = new UserDTO();
    userDTO.setUsername("alex");
    userDTO.setPassword("pass");
    userDTO.setId("12");
    Optional<UserDTO> optionalUserDTO = Optional.empty();
    when(mongoTemplate.findOne(Mockito.any(), Mockito.any())).thenReturn(userDTO);
    when(userRepository.findById(Mockito.any())).thenReturn(optionalUserDTO);
    ResponseDTO response = userService.deleteUser("alex");
    assertNotNull(response);
  }

  @Test
  @DisplayName("Usuarios por fecha de nacimiento")
  void userDate() {
    List<UserDateDTO> userDateDTOList = userService.getUserDateIterator("1");
    assertTrue(userDateDTOList.size() > 0);
  }
}