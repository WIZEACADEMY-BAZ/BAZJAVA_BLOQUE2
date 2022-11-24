package com.cursojava.proyecto.utils;
import java.util.Optional;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils <T>{

    public boolean isNotNullValue(Optional<T> value) {
        System.out.println(value);
        return (value != Optional.empty());
    }


    // Definicion del patron para validar sonido
    /**
     * ^           Indica el inicio de la declaracion
     * [A-ZÁÉÍÓÚ]  Debe contener una letra mayuscula inicial
     * [a-zñáéíóú]{3,5} Debe contener de 3 a 5 minusculas formando el sonido
     * $           Indica el final de la declaracion
     */
    private static final String SOUND_PATTERN = "^[A-ZÁÉÍÓÚ][a-zñáéíóú]{3,5}$";

    // Definicion del patron para validar formato de fecha (dd-mm-yyyy)
    private static final String DATETIME_PATTERN= "^\\d{2}-\\d{2}-\\d{4}$";
    private static final Pattern pattern_sound = Pattern.compile(SOUND_PATTERN);
    private static Pattern pattern_datetime = Pattern.compile(DATETIME_PATTERN);

    public static long randomAcountNumber() {
        return new Random().nextLong();
    }


    public static boolean isSoundValid(String sound) {
        Matcher matcher = pattern_sound.matcher(sound);
        return matcher.matches();
    }

    public static boolean isDateTimeValid(String datetime) {
        Matcher matcher = pattern_datetime.matcher(datetime);
        return matcher.matches();
    }
}