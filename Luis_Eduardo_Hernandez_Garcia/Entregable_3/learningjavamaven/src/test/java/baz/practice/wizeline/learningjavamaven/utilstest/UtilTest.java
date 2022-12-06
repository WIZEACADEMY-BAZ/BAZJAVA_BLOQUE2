package baz.practice.wizeline.learningjavamaven.utilstest;

import baz.practice.wizeline.learningjavamaven.utils.Utils;
import org.junit.jupiter.api.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UtilTest {

    private final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$]).{6,8}$";
    private final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
    private static Pattern DATE_PATTERN = Pattern.compile("^\\d{2}-\\d{2}-\\d{4}$");



    @DisplayName("Validando contraseña")
    @Test
    public void validarNulos(){
        //Configuracion (given)
        String passIncorrect = null;
        String passCorrect = "Pass3$";
        //Dentro del parentesis es tu Cuando (When)
        //Assertion es tu then
        Assertions.assertTrue(Utils.validateNullValue(passCorrect));
        Assertions.assertFalse(Utils.validateNullValue(passIncorrect));
    }

    @Test
    public void isPasswordValid() {
        //Configuracion (given)
        String passIncorrect="Password";
        String passCorrect="Pass3$";
        // Valida la contraseña contra el patron que definimos
        Assertions.assertTrue(Utils.isPasswordValid(passCorrect));
        Assertions.assertFalse(Utils.isPasswordValid(passIncorrect));
    }


}
