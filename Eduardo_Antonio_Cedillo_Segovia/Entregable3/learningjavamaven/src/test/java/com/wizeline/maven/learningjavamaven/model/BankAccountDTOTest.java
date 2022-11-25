package com.wizeline.maven.learningjavamaven.model;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.wizeline.maven.learningjavamaven.enums.AccountType.AHORRO;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankAccountDTOTest {

    @InjectMocks
    private BankAccountDTO bankAccountDTO;

    @Test
    void getAccountNumber() {
        bankAccountDTO.getAccountNumber();
    }

    @Test
    void setAccountNumber() {
        bankAccountDTO.setAccountNumber(1);
    }

    @Test
    void getAccountName() {
        bankAccountDTO.getAccountName();
    }

    @Test
    void setAccountName() {
        bankAccountDTO.setAccountName("ows");
    }

    @Test
    void getUserName() {
        bankAccountDTO.getUserName();
    }

    @Test
    void setUserName() {
        bankAccountDTO.setUserName("pws");
    }

    @Test
    void getAccountBalance() {
        bankAccountDTO.getAccountBalance();
    }

    @Test
    void setAccountBalance() {
        bankAccountDTO.setAccountBalance(12);
    }

    @Test
    void getAccountType() {
        bankAccountDTO.getAccountType();
    }

    @Test
    void setAccountType() {
        bankAccountDTO.setAccountType(AHORRO);
    }

    @Test
    void getCountry() {
        bankAccountDTO.getCountry();
    }

    @Test
    void setCountry() {
        bankAccountDTO.getCountry();
    }

    @Test
    void isAccountActive() {
        bankAccountDTO.isAccountActive();
    }

    @Test
    void setAccountActive() {

        bankAccountDTO.setAccountActive(true);
    }

    @Test
    void setCreationDate() {

    }

    @Test
    void getLastUsage() {
        bankAccountDTO.getLastUsage();
    }
}
