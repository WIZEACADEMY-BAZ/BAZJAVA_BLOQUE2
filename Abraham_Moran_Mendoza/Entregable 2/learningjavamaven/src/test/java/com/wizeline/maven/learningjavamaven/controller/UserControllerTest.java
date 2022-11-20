package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.PostDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.model.TodoDTO;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import com.wizeline.maven.learningjavamaven.repository.PostRepositoryImpl;
import com.wizeline.maven.learningjavamaven.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

  // Revisión: Uso de Mockito en cada prueba
  @Mock
  UserService userServiceMock;
  @Mock
  ResponseDTO responseDTOMock;
  @Mock
  TodoDTO todoDTOMock;
  @Mock
  PostDTO postDTOMock;
  @Mock
  UserDTO userDTOMock;

  // Revisión: Prueba unitaria de cada endpoint de la API
  @InjectMocks
  private UserController userController;

  @BeforeEach
  void init(){
    MockitoAnnotations.openMocks(this);
  }

  //
  // login
  //
  @Test
  void loginTest() {
    String user = "user";
    String password = "password";
    when(userServiceMock.getUserMongo(user,password)).thenReturn(responseDTOMock);
    assertNotNull(userController.login(user,password));
  }

  //
  // getGetUserTodos
  //
  @Test
  void getGetUserTodosTest() {
    String userId = "1";
    List<TodoDTO> todoDTOListMock = new ArrayList<>();
    todoDTOListMock.add(todoDTOMock);

    when(userServiceMock.getUserTodos(userId)).thenReturn(todoDTOListMock);
    assertNotNull(userController.getGetUserTodos(userId));
  }

  //
  // getGetUserPosts
  //
  @Test
  void getGetUserPostsTest() {
    String userId = "1";
    List<PostDTO> postDTOListMock = new ArrayList<>();
    postDTOListMock.add(postDTOMock);

    when(userServiceMock.getUserPosts(userId)).thenReturn(postDTOListMock);

    assertNotNull(userController.getGetUserPosts(userId));
  }

  //
  // getUserPostsIterator
  //
  // Revisión: Prueba unitaria de cada operación CRUD
  @Test
  void getUserPostsIteratorTest() {
    String userId = "1";
    List<PostDTO> postDTOListMock = new ArrayList<>();
    postDTOListMock.add(postDTOMock);

    when(userServiceMock.getUserPostsIterator(userId)).thenReturn(postDTOListMock);

    assertNotNull(userController.getUserPostsIterator(userId));
  }

  //
  // createUserPostsWithAttatchments
  //
  @Test
  void createUserPostsWithAttatchmentsTest() {
    String userId = "1";

    when(userServiceMock.createUserPostWithImage(userId)).thenReturn(postDTOMock);
    when(userServiceMock.createUserPostWithDocument(userId)).thenReturn(postDTOMock);
    when(userServiceMock.createUserPostWithImageAndDocument(userId)).thenReturn(postDTOMock);

    assertNotNull(userController.createUserPostsWithAttatchments(userId));
  }

  @Test
  void testGetGetUserTodosTest() {
    String userId = "1";
    boolean status = true;
    List<TodoDTO> todoDTOList = new ArrayList<>();
    todoDTOList.add(todoDTOMock);

    when(userServiceMock.getUserTodos(userId,status)).thenReturn(todoDTOList);

    assertNotNull(userController.getGetUserTodos(userId,status));
  }

  // Revisión: Prueba unitaria de cada operación CRUD
  @Test
  void createUserTest() {
    when(userServiceMock.createUserMongo(userDTOMock)).thenReturn(responseDTOMock);

    assertNotNull(userController.createUser(userDTOMock));
  }
}