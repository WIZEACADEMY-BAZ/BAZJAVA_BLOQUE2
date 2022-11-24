package com.wizeline.gradle.learningjavagradle;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Random;

import org.junit.jupiter.api.Test;

import com.wizeline.gradle.learningjavagradle.utils.Utils;

class UtilsTest {
	
	@Test
	public boolean isRFCvalidTest() {
		UtilsTest instance = new UtilsTest();
		boolean expResult = true;
		boolean result = instance.isRFCvalidTest();
	assertEquals(expResult, result);
	return result;	
	}
	
@Test
public String getStringTest() {
	UtilsTest instance = new UtilsTest();
	String expResult = "";
	String result = instance.getStringTest();
	assertEquals(expResult, result);
	return result;
}

@Test
public boolean validateNullValueTest() {
	UtilsTest instance = new UtilsTest();
	boolean expResult = false;
	boolean result = instance.validateNullValueTest();
	assertEquals(expResult, result);
	return false;
}

@Test
public boolean isPasswordValidTest() {
	UtilsTest instance = new UtilsTest();
	boolean expResult = true;
	boolean result = instance.isPasswordValidTest();
	assertEquals(expResult, result);
	return true;
}

@Test
public boolean isDateFormatValidTest() {
	UtilsTest instance = new UtilsTest();
	boolean expResult = true;
	boolean result = instance.isDateFormatValidTest();
	assertEquals(expResult, result);
	return true;	
}

@Test
public static long randomAcountNumberTest() {
	UtilsTest instance = new UtilsTest();
	long expResult =  Utils.randomAcountNumber();
	long result = instance.randomAcountNumberTest();
	assertEquals(expResult, result);
	return new Random().nextLong();
}


	/*@Test
	void test() {
		//fail("Not yet implemented");
	}
    */
	
}
