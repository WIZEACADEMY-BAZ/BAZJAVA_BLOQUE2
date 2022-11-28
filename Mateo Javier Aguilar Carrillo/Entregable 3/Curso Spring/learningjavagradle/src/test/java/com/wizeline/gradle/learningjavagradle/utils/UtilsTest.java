package com.wizeline.gradle.learningjavagradle.utils;

import com.wizeline.gradle.learningjavagradle.enums.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static com.wizeline.gradle.learningjavagradle.utils.Utils.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class UtilsTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UtilsTest.class);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getCountryTest() {
        LOGGER.info("getCountry Testing...");

        assertNotNull(getCountry(Country.MX));
    }

    @Test
    public void randomIntTest() {
        LOGGER.info("randomInt Testing...");

        assertNotNull(randomInt());
    }

    @Test
    public void randomBalanceTest() {
        LOGGER.info("randomBalance Testing...");

        assertNotNull(randomBalance());
    }

    @Test
    public void pickRandomAccountTypeTest() {
        LOGGER.info("pickRandomAccountType Testing...");

        assertNotNull(pickRandomAccountType());
    }

    @Test
    public void randomAccountNumberTest() {
        LOGGER.info("randomAccountNumber Testing...");

        assertNotNull(randomAcountNumber());
    }

    @Test
    public void isPasswordValidTest() {
        LOGGER.info("isPasswordValid Testing...");

        assertNotNull(isPasswordValid("sdgfadfnga"));
    }

    @Test
    public void isDateFormatValidTest() {
        LOGGER.info("isDateFormatValid Testing...");

        assertNotNull(isDateFormatValid("sdgfadfnga"));
    }

    @Test
    public void validateNullValueTest() {
        LOGGER.info("validateNullValue Testing...");

        assertTrue(validateNullValue("Hola"));
        assertFalse(validateNullValue(null));
    }

    @Test
    public void getStringTest() {
        LOGGER.info("getString Testing...");

        assertEquals(getString("sdgfadfnga"), "sdgfadfnga");
        assertEquals(getString(null), "");
    }
}
