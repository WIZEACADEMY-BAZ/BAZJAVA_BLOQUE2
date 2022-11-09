package com.wizeline.gradle.learningjavagradle.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.wizeline.gradle.learningjavagradle.enums.AccountType;
import com.wizeline.gradle.learningjavagradle.enums.Country;
import com.wizeline.gradle.learningjavagradle.model.BankAccountNomina;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Utils {

	private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$]).{6,8}$";
	
	/*
	 * Expresion regular para validar rfc
	 */
	private static final Pattern  RFC_PATTERN =Pattern.compile("[A-ZÑ&]{3,4}\\d{6}[A-V1-9][A-Z1-9][0-9A]");

	private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

	private static Pattern DATE_PATTERN = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");
	
	

	/*
	 * Metodo para validar RFC con expresiones regulares
	 */
	public static boolean isRFCvalid(String rfc) {
		return RFC_PATTERN.matcher(rfc).matches();
	}
	
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
	
	/*
	 * 
	 * METODO DE CIFRADO
	 * 
	 */
	public static BankAccountNomina cifrado(BankAccountNomina cuenta) throws ShortBufferException, IllegalBlockSizeException, BadPaddingException {
		byte[] keyBytes = new byte[]{
                0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef
        };
        byte[] ivBytes = new byte[]{
                0x00, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x01
        };
        /*
         * Definir un proveedor de cifrado
         */
        Security.addProvider(new BouncyCastleProvider());
        /*
         * Definir llaves de cifrado y algoritmo de cifrado DES
         */
        SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        Cipher cipher = null;
        
        try {
            cipher = Cipher.getInstance("DES/CTR/NoPadding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        
        /*
         * CIFRADO DE PARAMETROS DE LA CLASE
         */
        String accountName = cuenta.getAccountName();
        byte[] arrAccountName = accountName.getBytes();
        byte [] accountNameCipher = new byte[cipher.getOutputSize(arrAccountName.length)];
        int ctAccountNameLength = cipher.update(arrAccountName, 0, arrAccountName.length, accountNameCipher, 0);
        ctAccountNameLength += cipher.doFinal(accountNameCipher, ctAccountNameLength);
        cuenta.setAccountName(accountNameCipher.toString());
        
        String accountRFC = cuenta.getRfc();
        byte[] arrAccountRFC= accountRFC.getBytes();
        byte[] accountRFCCipher = new byte[cipher.getOutputSize(arrAccountRFC.length)];
        int ctAccountRFCLength = cipher.update(arrAccountRFC, 0, arrAccountRFC.length, accountRFCCipher, 0);
        ctAccountRFCLength += cipher.doFinal(accountRFCCipher, ctAccountRFCLength);
        cuenta.setRfc(accountRFCCipher.toString());
        
        String apellidos = cuenta.getApellidosUser();
        byte[] arrAccounApellidos= apellidos.getBytes();
        byte[] accountApellidosCipher = new byte[cipher.getOutputSize(arrAccounApellidos.length)];
        int ctAccountApellidosLength = cipher.update(arrAccounApellidos, 0, arrAccounApellidos.length, accountApellidosCipher, 0);
        ctAccountNameLength += cipher.doFinal(accountApellidosCipher, ctAccountApellidosLength);
        cuenta.setApellidosUser(accountApellidosCipher.toString());
		return cuenta;		
	}
}
