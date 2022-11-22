package com.wizeline.gradle.learningjavagradle.utils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import com.wizeline.gradle.learningjavagradle.enums.Country;

class UtilsTest {
	
	 int data = 1;

	    private static final Logger LOGGER = Logger.getLogger(UtilsTest.class.getName());
	    private Utils utils;

	    @Mock Country country;
	    
	    @BeforeEach
	    void testantesDeLasPrueba() {
	        data = 0;
	        LOGGER.info("Antes de cada prueba: " + data);
	        Utils utils = new Utils();
	    }

	    @Test
	    void testRandomAccountNumber(){
	        Utils.randomAcountNumber();
	    }

	    @Test
	    void testRandomBalance(){
	        utils.randomBalance();
	    }

	    @Test
	    void testPickRandomAccountType(){
	        utils.pickRandomAccountType();
	    }

	    @Test
	    void testRandomInt(){
	        utils.randomInt();
	    }

	    @Test
	    void testGetCountry(){
	        utils.getCountry(country);
	    }

	    @Test
	    void testIsPasswordValid(){
	        utils.isPasswordValid("");
	    }

	    @Test
	    void testIsDateFormatValid(){
	        utils.isDateFormatValid("10-12-202");
	    }


}
