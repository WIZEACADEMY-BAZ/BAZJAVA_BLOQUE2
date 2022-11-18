package com.wizeline.maven.learninjavamaven.utils;

import com.wizeline.maven.learninjavamaven.enums.Country;
import com.wizeline.maven.learninjavamaven.repository.UserRepositoryImpl;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Comparator;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UtilsTest {

    int data = 1;

    private static final Logger LOGGER = Logger.getLogger(UtilsTest.class.getName());
    private Utils utils;

    @Mock Country country;

    @BeforeEach
    void antesDeCadaPrueba() {
        data = 0;
        System.out.println("Antes de cada prueba: " + data);
        Utils utils = new Utils();
    }

    @Test
    void randomAccountNumberTest(){
        Utils.randomAcountNumber();
    }

    @Test
    void randomBalanceTest(){
        utils.randomBalance();
    }

    @Test
    void pickRandomAccountTypeTest(){
        utils.pickRandomAccountType();
    }

    @Test
    void randomIntTest(){
        utils.randomInt();
    }

    @Test
    void getCountryTest(){
        utils.getCountry(country);
    }

    @Test
    void isPasswordValid(){
        utils.isPasswordValid("");
    }

    @Test
    void isDateFormatValidTest(){
        utils.isDateFormatValid("10-12-202");
    }

}
