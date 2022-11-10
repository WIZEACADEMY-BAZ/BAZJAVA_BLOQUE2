package com.wizeline.entregabledavid.utils;

import com.wizeline.entregabledavid.enums.AccountType;
import com.wizeline.entregabledavid.enums.Country;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    // Definicion del patron para validar contraseña
    /**
     * ^           Indica el inicio de la declaracion
     * (?=.*[0-9]) Debe contener un digito del 1 al 9
     * (?=.*[a-z]) Debe contener una letra minuscula
     * (?=.*[A-Z]) Debe contener una letra mayuscula
     * (?=.*[@#$]) Debe contener un caracter especial de los indicados entre corchetes
     * {6,8}       Debe tener una longitud de entre 6 a 8 caracteres
     * $		   Indica el final de la declaracion
     */
    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$]).{6,8}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    // Definicion del patron para validar formato de fecha (dd-mm-yyyy)
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
        // Valida la contraseña contra el patron que definimos
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isDateFormatValid(String date) {
        // Valida la fecha contra el patron que definimos
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

    public static String cifrarBalance(Double balance){
        // Aquí implementaremos nuestro código de cifrar nuestras cuentas y regresarselas al usuario de manera cifrada
        byte[] keyBytes = new byte[]{
                0x01, 0x23, 0x45, 0x67, (byte) 0x89, (byte) 0xab, (byte) 0xcd, (byte) 0xef
        };
        byte[] ivBytes = new byte[]{
                0x00, 0x01, 0x02, 0x03, 0x00, 0x00, 0x00, 0x01
        };
        Security.addProvider(new BouncyCastleProvider());
        SecretKeySpec key = new SecretKeySpec(keyBytes, "DES");
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("DES/CTR/NoPadding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);
            // Cifraremos solamente el nombre y el country (pueden cifrar todos los parámetros que gusten)

            byte[] arrBalance = balance.toString().getBytes();
            byte [] accountBalanceCipher = new byte[cipher.getOutputSize(arrBalance.length)];
            int ctBalanceLength = cipher.update(arrBalance, 0, arrBalance.length, accountBalanceCipher, 0);
            ctBalanceLength += cipher.doFinal(accountBalanceCipher, ctBalanceLength);
            String balanceCifrado = accountBalanceCipher.toString();
            return balanceCifrado;

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchProviderException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        } catch (ShortBufferException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

    }
}