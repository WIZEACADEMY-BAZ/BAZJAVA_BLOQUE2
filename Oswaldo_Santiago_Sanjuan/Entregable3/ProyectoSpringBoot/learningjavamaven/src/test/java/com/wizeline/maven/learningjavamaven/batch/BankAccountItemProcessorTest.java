package com.wizeline.maven.learningjavamaven.batch;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.wizeline.maven.learningjavamaven.enums.AccountType.AHORRO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)

class BankAccountItemProcessorTest {

    @InjectMocks
    private BankAccountItemProcessor bankAccountItemProcessor;

    @Test
    void process() {
        BankAccountDTO o = new BankAccountDTO();
        o.setAccountNumber(2);
        o.setCountry("mexico");
        o.setAccountName("ert");
        o.setAccountBalance(3);
        o.setUserName("wre");
        o.setAccountType(AHORRO);

        try {
            bankAccountItemProcessor.process(o);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}