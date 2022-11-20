package com.wizeline.maven.learningjavamaven.paterns.creational.builder;

import com.wizeline.maven.learningjavamaven.model.PostDTO;
import com.wizeline.maven.learningjavamaven.paterns.creational.builder.builders.PostBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostDirectorTest {

  @Mock
  PostBuilder postBuilderMock;

  @Mock
  PostDTO postDTOMock;

  @InjectMocks
  PostDirector postDirector;

  @BeforeEach
  void init(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void setPostBuilder() {
    String id = "id";
    doNothing().when(postBuilderMock).addAttatchment();
    doNothing().when(postBuilderMock).addDate(any(Date.class));
    postDirector.buildPost();
    verify(postBuilderMock).addAttatchment();
    assertNotNull(id);
  }

  @Test
  void getFinishedPost() {
    when(postBuilderMock.getPostDTO()).thenReturn(postDTOMock);
    assertNotNull(postDirector.getFinishedPost());
  }
}