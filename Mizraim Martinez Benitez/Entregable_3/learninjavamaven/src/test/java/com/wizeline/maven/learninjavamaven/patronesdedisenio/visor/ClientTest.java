package com.wizeline.maven.learninjavamaven.patronesdedisenio.visor;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ClientTest {

    @InjectMocks
    private Client clientTest;

    @Test
    void mainTest(){
        String[] args = new String[0];
        clientTest.main(args);
    }
}
