package com.wizeline.baz.cipher;

import java.util.Base64;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class DESCipher {
	
	private final byte[] ivBytes = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x01 };
	byte[] keyBytes = new byte[] { 0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef };
	private static final Logger LOGGER = Logger.getLogger(DESCipher.class.getName());
	
	/**
	 * Return the encrypted text in base64 format
	 * @param inputStr
	 * @return
	 */
	@Nullable
	public String encrypt(String inputStr) {
		byte[] input = inputStr.getBytes();
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "DES");
	    IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
	    String result = null;
	    try {
	    	Cipher cipher = Cipher.getInstance("DES/CTR/NoPadding", "BC");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
			byte[] cipherText = new byte[cipher.getOutputSize(input.length)];
		    int ctLength = cipher.update(input, 0, input.length, cipherText, 0);
		    cipher.doFinal(cipherText, ctLength);
		    result = Base64.getEncoder().encodeToString(cipherText);
		} catch (Exception e) {
			LOGGER.info(String.format("No se pudo cifrar el dato '%s'. Excepcion: %s", inputStr, e.getClass().getName() ));
		}
	    return result;
	}
	
	/**
	 * Decode base64 encrypted text
	 * @param inputStr
	 * @return
	 */
	@Nullable
	public String decrypt(String inputStr) {
		byte[] input = Base64.getDecoder().decode(inputStr.getBytes());
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "DES");
	    IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
	    String result = null;
	    try {
	    	Cipher cipher = Cipher.getInstance("DES/CTR/NoPadding", "BC");
			cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
			byte[] plainText = new byte[cipher.getOutputSize(input.length)];
		    int ctLength = cipher.update(input, 0, plainText.length, plainText, 0);
		    cipher.doFinal(plainText, ctLength);
		    result = new String(plainText);
		} catch (Exception e) {
			LOGGER.info(String.format("No se pudo descifrar el dato '%s'. Excepcion: %s", inputStr, e.getClass().getName() ));
		}
	    return result;	    
	}
}
