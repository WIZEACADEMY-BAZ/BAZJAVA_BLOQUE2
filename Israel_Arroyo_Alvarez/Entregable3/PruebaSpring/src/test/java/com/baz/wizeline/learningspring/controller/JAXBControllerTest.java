package com.baz.wizeline.learningspring.controller;

import com.baz.wizeline.learningspring.model.XmlBean;
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
    @DisplayName("JAXB")
    void loginUser() {
        ResponseEntity<XmlBean> response = jaxbController.loginUser();
        assertNotNull(response);
    }

}