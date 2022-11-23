package com.baz.wizeline.learningspring.utils;

import com.baz.wizeline.learningspring.model.BankAccountDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UtilsTest {

    BankAccountDTO bankAccountDTO;

    @BeforeEach
    void setUp() {

        bankAccountDTO = new BankAccountDTO();


    }

    @Test
    void nombreCuentaOptionalNull(){
        bankAccountDTO.setUserName("");
        Utils.nombreCuentaOptional(bankAccountDTO);
    }

    @Test
    void nombreCuentaOptionalEmpty(){
        bankAccountDTO.setUserName(null);
        Utils.nombreCuentaOptional(bankAccountDTO);
    }

    @Test
    void nombreCuentaOptionalCorrect(){
        bankAccountDTO.setUserName("Hola");
        Utils.nombreCuentaOptional(bankAccountDTO);
    }


}