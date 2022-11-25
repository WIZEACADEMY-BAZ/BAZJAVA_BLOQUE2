package com.wizeline.maven.learningjavamaven.chainofresponsibility;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MoneyChainHandlerTest {

    @InjectMocks
    private MoneyChainHandler moneyChainHandler;


    @Test
    void setNextHandler() {

    }

    @Test
    void handler() {
    }
}
