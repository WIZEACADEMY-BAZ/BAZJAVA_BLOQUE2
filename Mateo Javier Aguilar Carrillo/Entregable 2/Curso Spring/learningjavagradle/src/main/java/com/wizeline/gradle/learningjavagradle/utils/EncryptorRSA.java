package com.wizeline.gradle.learningjavagradle.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.regex.Pattern;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Component;

@Component
public class EncryptorRSA {
	private static PublicKey publicKey;
	private static PrivateKey privateKey;

	static {
		try {
			publicKey = getPublicKey(Constants.PUBLIC_KEY_RSA);
			privateKey = getPrivateKey(Constants.PRIV_KEY_RSA);
		} catch (Exception e) {
			throw new RuntimeException("No se pudo cargar llaves de RSA", e);
		} 
	}

	private static PublicKey getPublicKey(String base64PublicKey) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		ClassLoader classLoader = EncryptorRSA.class.getClassLoader();
		InputStream fis = classLoader.getResourceAsStream(base64PublicKey);
		byte[] publicKeyBytes = IOUtils.toByteArray(fis);
		fis.close();

		publicKeyBytes = decodeKey(publicKeyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
		PublicKey publicKey = keyFactory.generatePublic(keySpec);

		return publicKey;

	}

	private static PrivateKey getPrivateKey(String base64PrivateKey) throws IOException, NoSuchAlgorithmException, InvalidKeySpecException {
		ClassLoader classLoader = EncryptorRSA.class.getClassLoader();
		InputStream fis = classLoader.getResourceAsStream(base64PrivateKey);
		byte[] privateKeyBytes = IOUtils.toByteArray(fis);
		fis.close();
		privateKeyBytes = decodeKey(privateKeyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		PrivateKey privateKey = keyFactory.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

		return privateKey;
	}

	public String encrypt(String data) 
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance(Constants.ALGORITHM_RSA);
		cipher.init(cipher.ENCRYPT_MODE, publicKey);
		
		byte[] encriptado = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
		return Base64.encodeBase64String(encriptado);
	}
	
	public String decrypt(byte[] data) 
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException  {
		Cipher cipher = Cipher.getInstance(Constants.ALGORITHM_RSA);
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		
		byte[] bytesDesencriptados = cipher.doFinal(data);
		return new String(bytesDesencriptados).trim();
	}


	private static byte[] decodeKey(byte[] keyBytes) {
		String pem = new String(keyBytes, StandardCharsets.ISO_8859_1);
		Pattern parse = Pattern.compile("(?m)(?s)^---*BEGIN.*---*$(.*)^---*END.*---*$.*");
		String encoded = parse.matcher(pem).replaceFirst("$1");
		return Base64.decodeBase64(encoded);
	}

}
