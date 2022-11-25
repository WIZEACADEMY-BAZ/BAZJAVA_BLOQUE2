package com.wizeline.maven.learningjavamaven.model;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SavingTest {
    @InjectMocks
    private Saving saving;

    @Test
    void getAccountNumber() {
        saving.getAccountNumber();
    }

    @Test
    void setAccountNumber() {
        saving.setAccountNumber(1);
    }

    @Test
    void tieneFondos() {
        saving.tieneFondos();
    }

    @Test
    void getEfectivo() {
        saving.getEfectivo();
    }

    @Test
    void setEfectivo() {
        saving.setEfectivo(23);
    }
}
