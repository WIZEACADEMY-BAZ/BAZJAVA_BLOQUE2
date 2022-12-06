package com.wizeline.maven.learningjavamaven.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JAXBControllerTest {

  @InjectMocks
  JAXBController jAXBController;

  @BeforeEach
  void init(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void loginUser() {
    assertNotNull(jAXBController.loginUser());
  }
}