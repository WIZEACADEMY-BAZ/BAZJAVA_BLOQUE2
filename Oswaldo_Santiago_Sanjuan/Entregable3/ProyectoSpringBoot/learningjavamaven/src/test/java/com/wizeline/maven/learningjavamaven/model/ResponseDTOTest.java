package com.wizeline.maven.learningjavamaven.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ResponseDTOTest {

    @InjectMocks
    private ResponseDTO responseDTO;

    @Test
    void getStatus() {
        responseDTO.getStatus();
    }

    @Test
    void setStatus() {
        responseDTO.setStatus("activo");
    }

    @Test
    void getCode() {
        responseDTO.setCode("sdf");
    }

    @Test
    void setCode() {
        responseDTO.setCode("ewerew");
    }

    @Test
    void getErrors() {
        responseDTO.getErrors();
    }

    @Test
    void setErrors() {
        ErrorDTO o = new ErrorDTO();
        responseDTO.setErrors(o);

    }
}