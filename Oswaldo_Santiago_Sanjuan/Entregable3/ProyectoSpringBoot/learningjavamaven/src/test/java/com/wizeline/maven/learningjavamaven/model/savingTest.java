package com.wizeline.maven.learningjavamaven.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class savingTest {

    @InjectMocks
    private saving savin;

    @Test
    void getAccountNumber() {
        savin.getAccountNumber();
    }

    @Test
    void setAccountNumber() {
        savin.setAccountNumber(1);
    }

    @Test
    void tieneFondos() {
        savin.tieneFondos();
    }

    @Test
    void getEfectivo() {
        savin.getEfectivo();
    }

    @Test
    void setEfectivo() {
        savin.setEfectivo(23);
    }
}