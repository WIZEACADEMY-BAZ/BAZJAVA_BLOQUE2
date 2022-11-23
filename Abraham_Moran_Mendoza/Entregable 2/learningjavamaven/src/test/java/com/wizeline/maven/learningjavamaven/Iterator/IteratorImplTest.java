package com.wizeline.maven.learningjavamaven.Iterator;

import com.wizeline.maven.learningjavamaven.model.PostDTO;
import com.wizeline.maven.learningjavamaven.paterns.behavioral.Iterator.IteratorImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IteratorImplTest {

  // Revisión: Uso de Mockito en cada prueba
  @Mock
  PostDTO postDTOMock;

  // Revisión: Prueba unitaria de cada endpoint de la API
  @BeforeEach
  void init(){
    MockitoAnnotations.openMocks(this);
  }

  //
  // hasNext
  //
  // Revisión: Pruebas para Happy Path
  @Test
  void hasNextUserIdEqualsTest() {
    String userId = "1";
    List<PostDTO> postDTOListMock = new ArrayList<>();
    postDTOListMock.add(postDTOMock);

    when(postDTOMock.getUserId()).thenReturn(userId);

    IteratorImpl iterator = new IteratorImpl(userId,postDTOListMock);

    assertEquals(iterator.hasNext(),true);
  }

  //Revisión: Pruebas para cada Edge Case
  @Test
  void hasNextUserIdNotEqualsTest() {
    String userId = "1";
    String anotherUserId = "2";
    List<PostDTO> postDTOListMock = new ArrayList<>();
    postDTOListMock.add(postDTOMock);

    when(postDTOMock.getUserId()).thenReturn(anotherUserId);

    IteratorImpl iterator = new IteratorImpl(userId,postDTOListMock);

    assertEquals(iterator.hasNext(),false);
  }

  //
  // next
  //
  @Test
  void next() {
    String userId = "1";
    String anotherUserId = "2";
    List<PostDTO> postDTOListMock = new ArrayList<>();
    postDTOListMock.add(postDTOMock);

    when(postDTOMock.getUserId()).thenReturn(anotherUserId);

    IteratorImpl iterator = new IteratorImpl(userId,postDTOListMock);

    assertNotNull(iterator.next());
  }
}