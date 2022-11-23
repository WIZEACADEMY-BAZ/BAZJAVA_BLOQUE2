package com.wizeline.maven.learningjavamaven.utils;

import com.wizeline.maven.learningjavamaven.enums.AccountType;
import com.wizeline.maven.learningjavamaven.enums.Country;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static String getString(String value){
        if(value != null){
            return value;
        }
        return "";
    }

    public static boolean validateNullValue(String value){
        if(value != null){
            return true;
        }
        return false;
    }

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$]).{6,8}$";
    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    private static Pattern DATE_PATTERN = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");

    public static boolean isPasswordValid(String password) {
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean isDateFormatValid(String date) {
        return DATE_PATTERN.matcher(date).matches();
    }

    public static long randomAcountNumber(){
        return new Random().nextLong();
    }

    public static double randomBalance(){
        return new Random()
                .doubles(1000,9000)
                .limit(1)
                .findFirst()
                .getAsDouble();
    }

    public static AccountType pickRandomAccountType(){
        AccountType[] accountTypes = AccountType.values();
        return accountTypes[new Random().nextInt(accountTypes.length)];
    }

    public static String randomInt() {
        return String.valueOf(new Random().ints(1, 10)
                .findFirst()
                .getAsInt());
    }

    public static String getCountry(Country country){
        Map<Country, String> countries = new HashMap<>();
        countries.put(Country.US, "United States");
        countries.put(Country.MX, "Mexico");
        countries.put(Country.FR, "France");
        return countries.get(country);
    }

    public static String cifrarDato(String dato){
        //Cifrado
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
        try{
            cipher = Cipher.getInstance("DES/CTR/NoPadding", "BC");
            cipher.init(Cipher.ENCRYPT_MODE, key, ivSpec);

            byte[] arrPassword = dato.getBytes();
            byte [] passwordCipher = new byte[cipher.getOutputSize(arrPassword.length)];
            int ctAccountNameLength = cipher.update(arrPassword, 0, arrPassword.length, passwordCipher, 0);
            ctAccountNameLength += cipher.doFinal(passwordCipher, ctAccountNameLength);
            return passwordCipher.toString();

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


    public static Map<String, String> convertToMap(String data){
        data = data.replace("\"","");
        data = data.substring(1, data.length()-1);
        String[] keyValuePairs = data.split(",");
        Map<String,String> map = new HashMap<>();

        for(String pair : keyValuePairs) {
            String[] entry = pair.split(":");
            map.put(entry[0].trim(), entry[1].trim());
        }
        return map;
    }


    public static <T> void muestraMensajeConsola(ArrayList<T> datos){
        datos.stream().forEach((elemento) ->{
            System.out.print(elemento+"");
        });
    }


}