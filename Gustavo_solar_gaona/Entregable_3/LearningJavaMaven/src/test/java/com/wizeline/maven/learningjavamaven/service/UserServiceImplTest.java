package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.*;
import com.wizeline.maven.learningjavamaven.repository.PostRepository;
import com.wizeline.maven.learningjavamaven.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @Mock
    private UserDTO userDTO;

    @Mock
    private PostDTO postDTO;

    @Mock
    private TodoDTO todoDTO;

    @Mock
    private ResponseDTO responseDTO;

    @Mock
    private UserServiceImpl.TodosHelper todosHelper;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private RestTemplate restTemplate;

    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        System.out.println("@BeforeEach => iniciando");
    }

    @Test
    void createUserMongoTest(){
        System.out.println("@Test => createUserMongoTest()");
        when(userRepository.save(userDTO)).thenReturn(userDTO);
        responseDTO.setCode("OK000");
        responseDTO.setStatus("success");
        assertAll(
                () -> assertEquals(userService.createUserMongo(userDTO).getStatus(), "success"),
                () -> assertEquals(userService.createUserMongo(userDTO).getCode(), "OK000"),
                () -> assertNotNull(userService.createUserMongo(userDTO))
        );
    }

    @Test
    void createUserMongoErrorTest(){
        System.out.println("@Test => createUserMongoErrorTest()");
        String msgError = "No se pudo crear el usuario";
        String code = "ER001";
        when(userRepository.save(userDTO)).thenReturn(null);
        responseDTO.setCode(code);
        responseDTO.setStatus(msgError);
        assertAll(
                () -> assertEquals(userService.createUserMongo(userDTO).getStatus(), msgError),
                () -> assertEquals(userService.createUserMongo(userDTO).getCode(), code),
                () -> assertNotNull(userService.createUserMongo(userDTO))
        );
    }

    @Test
    void getUserMongoTest(){
        System.out.println("@Test => getUserMongoTest()");
        String msgOk = "success";
        String code = "OK000";
        when(mongoTemplate.findOne(any(Query.class), eq(UserDTO.class))).thenReturn(userDTO);
        responseDTO.setCode(code);
        responseDTO.setStatus(msgOk);
        assertAll(
                () -> assertEquals(userService.getUserMongo("user", "password").getStatus(), msgOk),
                () -> assertEquals(userService.getUserMongo("user", "password").getCode(), code),
                () -> assertNotNull(userService.getUserMongo("user", "password"))
        );
    }

    @Test
    void getUserMongoErrorTest(){
        System.out.println("@Test => getUserMongoErrorTest()");
        String msgError = "Usuario o password incorrecto";
        String code = "ER001";
        when(mongoTemplate.findOne(any(Query.class), eq(UserDTO.class))).thenReturn(null);
        responseDTO.setCode(code);
        responseDTO.setStatus(msgError);
        assertAll(
                () -> assertEquals(userService.getUserMongo("user", "password").getStatus(), msgError),
                () -> assertEquals(userService.getUserMongo("user", "password").getCode(), code),
                () -> assertNotNull(userService.getUserMongo("user", "password"))
        );
    }

    @Test
    void getUserPostsTest(){
        System.out.println("@Test => getUserPostsTest()");
        List<PostDTO> postDTOList = new ArrayList<PostDTO>();
        postDTOList.add(postDTO);
        when(postRepository.getUserPosts("id")).thenReturn(postDTOList);
        assertAll(
                () -> assertEquals(userService.getUserPosts("userId").size(), 0)
        );
    }

    @Test
    void getUserTodosTest(){
        System.out.println("@Test => getUserTodosTest()");
        List<TodoDTO> todoDTOList = new ArrayList<TodoDTO>();
        todoDTOList.add(todoDTO);
        when(todosHelper.covertDTOs(todoDTOList, true)).thenReturn(todoDTOList);
        assertNotNull(userService.getUserTodos("userId", true));
    }

}