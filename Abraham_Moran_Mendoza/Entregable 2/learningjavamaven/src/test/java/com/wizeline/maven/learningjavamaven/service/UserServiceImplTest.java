package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.Iterator.PostDTOCollection;
import com.wizeline.maven.learningjavamaven.builder.PostDirector;
import com.wizeline.maven.learningjavamaven.builder.builders.PostBuilder;
import com.wizeline.maven.learningjavamaven.configuration.RestTemplateClient;
import com.wizeline.maven.learningjavamaven.model.PostDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.model.TodoDTO;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static com.wizeline.maven.learningjavamaven.constants.Constants.*;

class UserServiceImplTest {

  @Mock
  UserDTO userDTOMock;
  @Mock
  UserRepository userRepositoryMock;
  @Mock
  MongoTemplate mongoTemplateMock;
  @Mock
  RestTemplateClient restTemplateClientMock;
  @Mock
  RestTemplate restTemplateMock;
  @Mock
  TodoDTO todoDTOMock;
  @Mock
  PostRepository postRepositoryMock;
  @Mock
  PostDTOCollection postDTOCollectionMock;
  @Mock
  PostDTO postDtoMock;
  @Mock
  PostDirector postDirectorMock;

  @InjectMocks
  UserServiceImpl userService;

  @BeforeEach
  void init(){
    MockitoAnnotations.openMocks(this);
  }

  //
  // createUserMongo
  //

  @Test
  void createUserMongoSuccessTest() {
    when(userRepositoryMock.save(any(UserDTO.class))).thenReturn(userDTOMock);

    ResponseDTO responseDTOMock = userService.createUserMongo(userDTOMock);

    assertAll(
        () -> assertNotNull(responseDTOMock)
        , () -> assertEquals(responseDTOMock.getCode(),SUCCESS_CODE)
    );
  }

  @Test
  void createUserMongoErrorTest() {
    when(userRepositoryMock.save(any(UserDTO.class))).thenReturn(null);

    ResponseDTO responseDTOMock = userService.createUserMongo(userDTOMock);

    assertAll(
        () -> assertNotNull(responseDTOMock)
        , () -> assertEquals(responseDTOMock.getCode(),ERROR_CODE)
    );
  }

  //
  // getUserMongo
  //

  @Test
  void getUserMongoSuccessTest() {
    String user = "user";
    String password = "";

    when(mongoTemplateMock.findOne(any(Query.class), eq(UserDTO.class))).thenReturn(userDTOMock);

    ResponseDTO responseDTO = userService.getUserMongo(user,password);

    assertAll(
        () -> assertNotNull(responseDTO)
        , ()-> assertEquals(responseDTO.getCode(), SUCCESS_CODE)
    );

    assertNotNull(userService.getUserMongo(user,password));
  }

  @Test
  void getUserMongoErrorTest() {
    String user = "user";
    String password = "";

    when(mongoTemplateMock.findOne(any(Query.class), eq(UserDTO.class))).thenReturn(null);

    ResponseDTO responseDTO = userService.getUserMongo(user,password);

    assertAll(
        () -> assertNotNull(responseDTO)
        , () -> assertEquals(responseDTO.getCode(), ERROR_CODE)
    );
  }

  //
  // getUserTodos
  //

  @Test
  void getUserTodosTest() {
    String userId = "1";
    TodoDTO[] todoDTOsMock = new TodoDTO[1];
    todoDTOsMock[0] = todoDTOMock;

    when(restTemplateClientMock.restTemplate()).thenReturn(restTemplateMock);

    when(restTemplateMock.getForObject(anyString(), eq(TodoDTO[].class))).thenReturn(todoDTOsMock);

    assertNotNull(userService.getUserTodos(userId));
  }

  //
  // getUserTodos
  //

  @Test
  void testGetUserTodosFilteredByUserIdTest() {
    String userId = "1";
    int userIdNumber = 1;
    boolean status = false;
    TodoDTO[] todoDTOsMock = new TodoDTO[1];
    todoDTOsMock[0] = todoDTOMock;

    when(restTemplateClientMock.restTemplate()).thenReturn(restTemplateMock);

    when(restTemplateMock.getForObject(anyString(), eq(TodoDTO[].class))).thenReturn(todoDTOsMock);

    when(todoDTOMock.getCompleted()).thenReturn(status);

    when(todoDTOMock.getUserId()).thenReturn(userIdNumber);

    assertNotNull(userService.getUserTodos(userId,status));
  }

  @Test
  void testGetUserTodosNonFilteredTest() {
    String userId = "1";
    int userIdNumber = 1;
    boolean status = false;
    TodoDTO[] todoDTOsMock = new TodoDTO[1];
    todoDTOsMock[0] = todoDTOMock;

    when(restTemplateClientMock.restTemplate()).thenReturn(restTemplateMock);

    when(restTemplateMock.getForObject(anyString(), eq(TodoDTO[].class))).thenReturn(todoDTOsMock);

    when(todoDTOMock.getCompleted()).thenReturn(status);

    when(todoDTOMock.getUserId()).thenReturn(2);

    assertNotNull(userService.getUserTodos(userId,status));
  }

  //
  // getUserPosts
  //
  @Test
  void getUserPostsTest() {
    String userId = "1";
    List<PostDTO> postDTOListMock = new ArrayList<>();

    when(postRepositoryMock.getUserPosts(anyString())).thenReturn(postDTOListMock);

    assertNotNull(userService.getUserPosts(userId));
  }

  //
  // getUserPostsIterator
  //
  @Test
  void getUserPostsIteratorTest() {
    String userId = "1";
    List<PostDTO> postDTOListMock = new ArrayList<>();
    postDTOListMock.add(postDtoMock);

    when(postDtoMock.getUserId()).thenReturn(userId);
    when(postRepositoryMock.getAllPosts()).thenReturn(postDTOListMock);

    assertNotNull(userService.getUserPostsIterator(userId));
  }

  //
  // createUserPostWithImage
  //
  @Test
  void createUserPostWithImage() {
    String userId = "1";

    doNothing().when(postDirectorMock).setPostBuilder(any(PostBuilder.class));
    doNothing().when(postDirectorMock).buildPost();

    when(postDirectorMock.getFinishedPost()).thenReturn(postDtoMock);

    assertNotNull(userService.createUserPostWithImage(userId));
  }

  //
  // createUserPostWithDocument
  //
  @Test
  void createUserPostWithDocumentTest() {
    String userId = "1";

    doNothing().when(postDirectorMock).setPostBuilder(any(PostBuilder.class));
    doNothing().when(postDirectorMock).buildPost();

    when(postDirectorMock.getFinishedPost()).thenReturn(postDtoMock);

    assertNotNull(userService.createUserPostWithDocument(userId));
  }

  //
  // createUserPostWithImageAndDocument
  //
  @Test
  void createUserPostWithImageAndDocumentTest() {
    String userId = "1";

    doNothing().when(postDirectorMock).setPostBuilder(any(PostBuilder.class));
    doNothing().when(postDirectorMock).buildPost();

    when(postDirectorMock.getFinishedPost()).thenReturn(postDtoMock);

    assertNotNull(userService.createUserPostWithImageAndDocument(userId));
  }
}