package com.wizeline.maven.learningjavamaven.utils;

import com.wizeline.maven.learningjavamaven.enums.Country;
import joptsimple.internal.Strings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class UtilsTest {

  @BeforeEach
  void init(){
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void randomAcountNumberTest() {
    assertNotNull(Utils.randomAcountNumber());
  }

  @Test
  void randomBalanceTest() {
    assertNotNull(Utils.pickRandomAccountType());
  }

  @Test
  void pickRandomAccountTypeTest() {
    assertNotNull(Utils.pickRandomAccountType());
  }

  @Test
  void randomIntTest() {
    assertNotNull(Utils.randomInt());
  }

  @Test
  void getCountryTest() {
    Country countryMock = Country.MX;
    assertNotNull(Utils.getCountry(countryMock));
  }

  @Test
  void isPasswordValidTest() {
    String password = "Pass@1";
    String invalidPass = "Pass";
    assertAll( () -> assertTrue(Utils.isPasswordValid(password))
        , () -> assertFalse(Utils.isPasswordValid(invalidPass)) );
  }

  @Test
  void isDateFormatValidTest() {
    String date = "12-10-2022";
    String invalidDate = "222-222-2022";
    assertAll( () -> assertTrue(Utils.isDateFormatValid(date))
        , () -> assertFalse(Utils.isDateFormatValid(invalidDate)) );
  }

  @Test
  void getStringTest() {
    String value = "testString";
    assertAll( () -> assertEquals(Utils.getString(value),value)
        , () -> assertEquals(Utils.getString(null),Strings.EMPTY) );
  }

  @Test
  void validateNullValue() {
    String value = "testSTring";
    assertAll( () -> assertTrue(Utils.validateNullValue(value))
        , () -> assertFalse(Utils.validateNullValue(null)) );
  }
}