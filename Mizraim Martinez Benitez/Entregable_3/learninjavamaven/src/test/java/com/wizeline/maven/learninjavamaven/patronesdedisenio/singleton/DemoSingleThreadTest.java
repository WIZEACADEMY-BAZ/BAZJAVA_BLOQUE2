package com.wizeline.maven.learninjavamaven.patronesdedisenio.singleton;

import com.wizeline.maven.learninjavamaven.controller.BankingAccountController;
import com.wizeline.maven.learninjavamaven.controller.BankingAccountControllerTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.logging.Logger;

@ExtendWith(MockitoExtension.class)
public class DemoSingleThreadTest {
    private static final Logger LOGGER = Logger.getLogger(DemoSingleThreadTest.class.getName());

    @InjectMocks
    private DemoSingleThread demoTest;

    @Test
    void mainTest(){
        String[] args = new String[0];
        demoTest.main(args);
    }
}
