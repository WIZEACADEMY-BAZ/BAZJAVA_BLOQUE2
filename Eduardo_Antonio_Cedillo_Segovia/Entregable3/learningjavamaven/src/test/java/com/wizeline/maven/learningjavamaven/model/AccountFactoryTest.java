package com.wizeline.maven.learningjavamaven.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.wizeline.maven.learningjavamaven.enums.AccountType.NOMINA;


@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AccountFactoryTest {

    @InjectMocks
    AccountFactory accountFactory;

    @Test
    void getcuenta() {
        accountFactory.getcuenta(NOMINA);
    }
}
