package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.PostDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.model.TodoDTO;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import com.wizeline.maven.learningjavamaven.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private ResponseDTO responseDTO;

    @Mock
    private TodoDTO todoDTO;

    @Mock
    private PostDTO postDTO;

    @Mock
    private UserDTO userDTO;

    @InjectMocks
    private UserController userController;

    @BeforeEach()
    void init(){
        System.out.println("@BeforeEach => iniciando");
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void loginTest() {
        System.out.println("@Test => loginTest()");
        when(userService.getUserMongo("user", "password")).thenReturn(responseDTO);
        assertAll(
                () -> assertNotNull(userController.login("user", "password")),
                () -> assertEquals(userController.login("user", "password").getStatusCode().value(), HttpStatus.OK.value())
        );
    }

    @Test
    void getGetUserTodosTest(){
        System.out.println("@Test => getGetUserTodosTest()");
        String userId = "userId";
        List<TodoDTO> todoDTOList = new ArrayList<TodoDTO>();
        todoDTOList.add(todoDTO);
        when(userService.getUserTodos(userId)).thenReturn(todoDTOList);
        assertAll(
                () -> assertNotNull(userController.getGetUserTodos(userId)),
                () -> assertEquals(userController.getGetUserTodos(userId).getBody(), todoDTOList),
                () -> assertEquals(userController.getGetUserTodos(userId).getStatusCode().value(), HttpStatus.OK.value(), "Estatus distinto a 200")
        );
    }

    @Test
    void getUserPostsTest(){
        System.out.println("@Test => getUserPostsTest()");
        String userId = "userId";
        List<PostDTO> postDTOList = new ArrayList<PostDTO>();
        postDTOList.add(postDTO);
        when(userService.getUserPosts(userId)).thenReturn(postDTOList);
        assertAll(
                () -> assertNotNull(userController.getUserPosts(userId)),
                () -> assertEquals(userController.getUserPosts(userId).getBody(), postDTOList),
                () -> assertEquals(userController.getUserPosts(userId).getStatusCode().value(), HttpStatus.OK.value(), "Estatus distinto a 200")
        );
    }

    @Test
    void getUserTodosTest(){
        System.out.println("@Test => getUserTodosTest()");
        String userId = "userId";
        boolean status = true;
        List<TodoDTO> todoDTOlist = new ArrayList<TodoDTO>();
        todoDTOlist.add(todoDTO);
        when(userService.getUserTodos(userId,status)).thenReturn(todoDTOlist);
        assertAll(
                () -> assertNotNull(userController.getUserTodos(userId, status)),
                () -> assertEquals(userController.getUserTodos(userId, status).getBody(), todoDTOlist),
                () -> assertEquals(userController.getUserTodos(userId, status).getStatusCode().value(), HttpStatus.OK.value(), "Estatus distinto a 200")
        );
    }

    @Test
    void createUserTest(){
        System.out.println("@Test => createUserTest()");
        //doNothing().when(userService).createUserMongo(userDTO);
        when(userService.createUserMongo(userDTO)).thenReturn(responseDTO);
        assertAll(
                () -> assertEquals(userController.createUser(userDTO).getBody(), responseDTO),
                () -> assertEquals(userController.createUser(userDTO).getStatusCode().value(), HttpStatus.OK.value(), "Estatus distinto a 200")
        );
    }

}