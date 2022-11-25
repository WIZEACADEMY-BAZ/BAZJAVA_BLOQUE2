package com.wizeline.maven.learningjavamaven.model;

import com.wizeline.maven.learningjavamaven.enums.AccountType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankAccountDTOUpdateTest {
    @InjectMocks
    private BankAccountDTOUpdate bankAccountDTOUpdate;

    @Test
    void getAccountNumber() {
        bankAccountDTOUpdate.getAccountNumber();
    }

    @Test
    void setAccountNumber() {
        bankAccountDTOUpdate.setAccountNumber(23);
    }

    @Test
    void getAccountName() {
        bankAccountDTOUpdate.getAccountName();
    }

    @Test
    void setAccountName() {
        bankAccountDTOUpdate.setAccountName("dsfds");
    }

    @Test
    void getUserName() {
        bankAccountDTOUpdate.getUserName();
    }

    @Test
    void setUserName() {
        bankAccountDTOUpdate.setUserName("dfds");
    }

    @Test
    void getAccountBalance() {
        bankAccountDTOUpdate.getAccountBalance();
    }

    @Test
    void setAccountBalance() {
        bankAccountDTOUpdate.setAccountBalance(3);
    }

    @Test
    void getAccountType() {
        bankAccountDTOUpdate.getAccountType();
    }

    @Test
    void setAccountType() {
        bankAccountDTOUpdate.setAccountType(AccountType.NOMINA);
    }

    @Test
    void getCountry() {
        bankAccountDTOUpdate.getCountry();
    }

    @Test
    void setCountry() {
        bankAccountDTOUpdate.setCountry("Ecatepc");
    }

    @Test
    void isAccountActive() {
        bankAccountDTOUpdate.isAccountActive();
    }

    @Test
    void setAccountActive() {
        bankAccountDTOUpdate.setAccountActive(true);
    }

    @Test
    void setCreationDate() {
    }

    @Test
    void getLastUsage() {
        bankAccountDTOUpdate.getLastUsage();
    }
}