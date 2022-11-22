package com.wizeline.maven.learninjavamaven.utils;

import com.wizeline.maven.learninjavamaven.enums.Country;
import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import java.util.logging.Logger;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UtilsTest {

    int data = 1;

    private static final Logger LOGGER = Logger.getLogger(UtilsTest.class.getName());


    @InjectMocks
    private Utils utilsMock;

    @Mock private Country country;

    @Mock
    Utils.Hello hello;

    @Test
    void randomAccountNumberTest(){
        utilsMock.randomAccountNumber();
    }


    @Test
    void randomBalanceTest(){
        utilsMock.randomBalance();
    }

    @Test
    void pickRandomAccountTypeTest(){
        utilsMock.pickRandomAccountType();
    }

    @Test
    void randomIntTest(){
        utilsMock.randomInt();
    }

    @Test
    void getCountryTest(){
        utilsMock.getCountry(country);
    }

    @Test
    void isPasswordValid(){
        utilsMock.isPasswordValid("");
    }

    @Test
    void isDateFormatValidTest(){
        utilsMock.isDateFormatValid("10-12-202");
    }

    @Test
    void getStringNotNullTest(){
        utilsMock.getString(" ");
    }

    @Test
    void getStringNullTest(){
        utilsMock.getString(null);
    }

    @Test
    void validateNullValueTest(){
        utilsMock.validateNullValue(" ");
    }

    @Test
    void validateNotNullValueTest(){
        utilsMock.validateNullValue(null);
    }

    @Test
    void HelloClass(){
        Utils.Hello hello1 = new Utils.Hello();
        hello1.setHelloMessage("");
        String helloMessage = hello1.getHelloMessage();
        assertNotNull(helloMessage);
    }
}
