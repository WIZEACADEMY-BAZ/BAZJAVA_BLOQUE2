package com.wizeline.maven.learningjavamaven.model;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

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
        responseDTO.setCode("prueba");
    }

    @Test
    void setCode() {
        responseDTO.setCode("pruebas");
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