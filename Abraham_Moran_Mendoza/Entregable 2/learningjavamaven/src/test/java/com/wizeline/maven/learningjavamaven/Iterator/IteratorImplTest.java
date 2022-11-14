package com.wizeline.maven.learningjavamaven.Iterator;

import com.wizeline.maven.learningjavamaven.model.PostDTO;
import com.wizeline.maven.learningjavamaven.repository.PostRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IteratorImplTest {

  @Mock
  PostDTO postDTOMock;

  @BeforeEach
  void init(){
    MockitoAnnotations.openMocks(this);
  }

  //
  // hasNext
  //
  @Test
  void hasNextUserIdEqualsTest() {
    String userId = "1";
    List<PostDTO> postDTOListMock = new ArrayList<>();
    postDTOListMock.add(postDTOMock);

    when(postDTOMock.getUserId()).thenReturn(userId);

    IteratorImpl iterator = new IteratorImpl(userId,postDTOListMock);

    assertEquals(iterator.hasNext(),true);
  }

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