package com.wizeline.learningjavamaven.controller;

import com.wizeline.learningjavamaven.model.XmlBean;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JAXBControllerTest {

  @InjectMocks
  JAXBController jaxbController;

  @Test
  @DisplayName("Logueo con jaxb")
  void loginUser() {
    ResponseEntity<XmlBean> response = jaxbController.loginUser();
    assertNotNull(response);
  }
}