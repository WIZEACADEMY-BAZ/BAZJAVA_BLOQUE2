package com.wizeline.maven.learningjavamaven.batch;


import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.wizeline.maven.learningjavamaven.enums.AccountType.AHORRO;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BankAccountItemProcessorTest {

    @InjectMocks
    private BankAccountItemProcessor bankAccountItemProcessor;

    @Test
    void process() {
        BankAccountDTO o = new BankAccountDTO();
        o.setAccountNumber(2);
        o.setCountry("Mexico");
        o.setAccountName("AccountName");
        o.setAccountBalance(3);
        o.setUserName("UserName");
        o.setAccountType(AHORRO);

        try {
            bankAccountItemProcessor.process(o);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
