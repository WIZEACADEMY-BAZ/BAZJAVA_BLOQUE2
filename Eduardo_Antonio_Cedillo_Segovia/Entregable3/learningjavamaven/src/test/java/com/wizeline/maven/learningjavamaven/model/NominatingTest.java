package com.wizeline.maven.learningjavamaven.model;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NominatingTest {
    @InjectMocks
    private Nominating nominating;
    @Test
    void getAccountNumber() {
        nominating.getAccountNumber();
    }

    @Test
    void setAccountNumber() {
        nominating.setAccountNumber(123);
    }

    @Test
    void tieneFondos() {
        nominating.tieneFondos();
    }

    @Test
    void getEfectivo() {
        nominating.getEfectivo();
    }

    @Test
    void setEfectivo() {
        nominating.setEfectivo(123);
    }
}
