package com.wizeline.maven.learningjavamaven.controller;

import com.wizeline.maven.learningjavamaven.model.XmlBean;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class JAXBControllerTest {
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

    @InjectMocks
    private JAXBController jAXBController;

    @Test
    void loginUser() {
        //Preparo el esenario de la prueba
        LOGGER.info("Entrando a realizar de getEncryp");
        //Ejecuta la logica a probar
        ResponseEntity<XmlBean> l = jAXBController.loginUser();
        //Llamada
        assertTrue(l.getStatusCode().is2xxSuccessful(), "El codigo de respuesta fue exitoso");
    }

    @Test
    void marshal() {

    }

    @Test
    void unmarshall() {
    }
}