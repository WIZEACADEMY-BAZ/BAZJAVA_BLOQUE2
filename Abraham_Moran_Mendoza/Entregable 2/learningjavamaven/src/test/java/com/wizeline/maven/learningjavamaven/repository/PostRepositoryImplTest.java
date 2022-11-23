package com.wizeline.maven.learningjavamaven.repository;

import com.wizeline.maven.learningjavamaven.model.PostDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostRepositoryImplTest {

  // Revisión: Uso de Mockito en cada prueba
  @Mock
  RestTemplate restTemlateMock = mock(RestTemplate.class);

  // Revisión: Prueba unitaria de cada endpoint de la API
  @InjectMocks
  private PostRepositoryImpl postRepository;

  @BeforeEach
  void init(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getUserPosts() {
    String userIdMock = "1";
    PostDTO[] postDTOSMock = new PostDTO[1];
    postDTOSMock[0] = new PostDTO();

    when(restTemlateMock.getForObject(anyString(), eq(PostDTO[].class))).thenReturn(postDTOSMock);

    assertNotNull(postRepository.getUserPosts(userIdMock));
  }

  @Test
  void getAllPosts() {
    PostDTO[] postDTOSMock = new PostDTO[1];
    postDTOSMock[0] = new PostDTO();

    when(restTemlateMock.getForObject(anyString(), eq(PostDTO[].class))).thenReturn(postDTOSMock);

    assertNotNull(postRepository.getAllPosts());
  }
}