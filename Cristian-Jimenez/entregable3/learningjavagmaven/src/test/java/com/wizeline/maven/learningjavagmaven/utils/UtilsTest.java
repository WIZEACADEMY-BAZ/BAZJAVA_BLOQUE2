package com.wizeline.maven.learningjavagmaven.utils;

import com.wizeline.maven.learningjavagmaven.enums.Country;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UtilsTest {
String date="01-10-2010", value,value2, password="Pass1$";
    @InjectMocks
    private Utils utilsMock;

    @Mock private Country country;

    @Test
    void getStringTest() {
        utilsMock.getString(value);
    }

    @Test
    void validateNullValueTest() {
        utilsMock.validateNullValue(value2);
    }

    @Test
    void isPasswordValidTest() {
        utilsMock.isPasswordValid(password);
    }

    @Test
    void isDateFormatValidTest() {
        utilsMock.isDateFormatValid(date);
    }

    @Test
    void randomAccountNumberTest(){ utilsMock.randomAccountNumber(); }

    @Test
    void randomBalanceTest() {
        utilsMock.randomBalance();
    }

    @Test
    void pickRandomAccountTypeTest() {
        utilsMock.pickRandomAccountType();
    }

    @Test
    void randomIntTest() {
        utilsMock.randomInt();
    }

    @Test
    void getCountry() {utilsMock.getCountry(country); }

}