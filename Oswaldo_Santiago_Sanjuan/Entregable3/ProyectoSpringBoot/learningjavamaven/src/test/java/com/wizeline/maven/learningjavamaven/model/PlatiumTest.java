package com.wizeline.maven.learningjavamaven.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PlatiumTest {
    @InjectMocks
    private Platium platium;

    @Test
    void getAccountNumber() {
        platium.getAccountNumber();

    }

    @Test
    void setAccountNumber() {
        platium.setAccountNumber(12);
    }

    @Test
    void tieneFondos() {
        platium.tieneFondos();
    }

    @Test
    void getEfectivo() {
        platium.getEfectivo();
    }

    @Test
    void setEfectivo() {
        platium.setEfectivo(123);
    }
}