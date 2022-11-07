package com.wizeline.gradle.learningjavagradle.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wizeline.gradle.learningjavagradle.enums.AccountType;
import com.wizeline.gradle.learningjavagradle.enums.Country;

public class Utils {

	private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$]).{6,8}$";
	private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

	private static Pattern DATE_PATTERN = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");

	public static String getString(String value) {

		if(value != null) {
			return value;
		}
		return "";
	}

	public static boolean validateNullValue(String value) {
		if(value != null) {
			return true;
		}
		return false;
	}

	public static boolean isPasswordValid(String password) {
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

	public static boolean isDateFormatValid(String date) {
		return DATE_PATTERN.matcher(date).matches();
	}

	public static long randomAcountNumber() {
		return new Random().nextLong();
	}

	public static double randomBalance() {
		return new Random()
				.doubles(1000, 9000)
				.limit(1)
				.findFirst()
				.getAsDouble();
	}

	public static AccountType pickRandomAccountType() {
		// Creando un arreglo a partir de valores ya definidos
		AccountType[] accountTypes = AccountType.values();
		return accountTypes[new Random().nextInt(accountTypes.length)];
	}

	public static String randomInt() {
		return String.valueOf(new Random().ints(1, 10)
				.findFirst()
				.getAsInt());
	}

	public static String getCountry(Country country) {
		Map<Country, String> countries = new HashMap<>();
		countries.put(Country.US, "United States");
		countries.put(Country.MX, "Mexico");
		countries.put(Country.FR, "France");
		return countries.get(country);
	}
}
