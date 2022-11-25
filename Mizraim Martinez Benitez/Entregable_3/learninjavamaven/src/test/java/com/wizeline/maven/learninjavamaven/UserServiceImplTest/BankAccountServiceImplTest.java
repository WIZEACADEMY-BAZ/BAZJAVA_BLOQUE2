package com.wizeline.maven.learninjavamaven.UserServiceImplTest;

import com.wizeline.maven.learninjavamaven.controller.BankingAccountControllerTest;
import com.wizeline.maven.learninjavamaven.enums.AccountType;
import com.wizeline.maven.learninjavamaven.enums.Country;
import com.wizeline.maven.learninjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learninjavamaven.model.UserDTO;
import com.wizeline.maven.learninjavamaven.repository.BankingAccountRepository;
import com.wizeline.maven.learninjavamaven.service.BankAccountServiceImpl;
import org.bson.codecs.jsr310.LocalDateTimeCodec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.mongodb.assertions.Assertions.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@ExtendWith(MockitoExtension.class)
public class BankAccountServiceImplTest {

    private static final Logger LOGGER = Logger.getLogger(BankAccountServiceImplTest.class.getName());

    @InjectMocks
    private BankAccountServiceImpl serviceTest;
    @Mock
    private MongoTemplate mongoTemplate;
    @Mock
    private BankingAccountRepository repositoryTest;

    BankAccountDTO bankAccountDTO = new BankAccountDTO();
    UserDTO userDTO = new UserDTO();

    @BeforeEach
    void before() {
        userDTO.setUser("user");
        userDTO.setPassword("pass");
        bankAccountDTO.setUser("bankUser");
        bankAccountDTO.setAccountNumber(1L);
        bankAccountDTO.setAccountType(AccountType.PLATINUM);
    }


    @Test
    void buildBankAccountTest() {
        LOGGER.info("getAccountsTest");
        String user= "user";
        boolean isActive = true;

        BankAccountDTO bankAccountDTO = serviceTest.buildBankAccount(user, isActive, Country.FR, LocalDateTime.now());

        assertNotNull(bankAccountDTO);
    }

    @Test
    void getAccountsTest(){
        LOGGER.info("getAccountsTest");
        List<BankAccountDTO> listBanksDTO = new ArrayList<>();
        listBanksDTO.add(bankAccountDTO);
        when(serviceTest.getAccounts()).thenReturn(null);
        when(mongoTemplate.findAll(BankAccountDTO.class)).thenReturn(listBanksDTO);
        List<BankAccountDTO> bankAccountDTOS = serviceTest.getAccounts();
        assertNotNull(bankAccountDTOS);
        assertNotNull(listBanksDTO);
    }

    @Test
    void getAccountByUserTest() {
        LOGGER.info("getAccountByUserTest");
        when(mongoTemplate.find(any(), any())).thenReturn(Collections.singletonList(bankAccountDTO));
        List<BankAccountDTO> bankAccountDTOS = serviceTest.getAccountByUser("user");
        assertTrue(bankAccountDTOS.size() > 0);
    }

    @Test
    void getEncryptedAccountTest() {
        LOGGER.info("getEncryptedAccountTest");
        List<BankAccountDTO> listBanksDTO = new ArrayList<>();
        listBanksDTO.add(bankAccountDTO);
        when(serviceTest.encryptedAccounts()).thenReturn(listBanksDTO);
        lenient().when(mongoTemplate.findOne(any(), any())).thenReturn(bankAccountDTO);
        lenient().when(mongoTemplate.findAll(BankAccountDTO.class)).thenReturn(listBanksDTO);
        List<BankAccountDTO> bankAccountDTOS = serviceTest.encryptedAccounts();
        assertTrue(bankAccountDTOS.size() > 0);
    }

    @Test
    void getAccountDetailsTest() {
        LOGGER.info("getAccountDetailsTest");
        String date = "22-11-2022";
        BankAccountDTO bankAccountDTO = serviceTest.getAccountDetails("user", date);
        assertNotNull(bankAccountDTO);
    }

    @Test
    void deleteAccountsTest() {
        LOGGER.info("deleteAccountsTest");
        doNothing().when(repositoryTest).deleteAll();
        serviceTest.deleteAccounts();
    }

    @Test
    void saveTest(){
        LOGGER.info("saveTest");
        when(mongoTemplate.save(any())).thenReturn(bankAccountDTO);
        BankAccountDTO accountDTO = serviceTest.save(bankAccountDTO);
        assertNotNull(accountDTO);
    }

}
