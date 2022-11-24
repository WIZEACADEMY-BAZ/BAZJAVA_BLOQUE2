package com.wizeline.learningspring.service;

import com.wizeline.learningspring.enums.AccountType;
import com.wizeline.learningspring.model.BankAccountDTO;
import com.wizeline.learningspring.model.UserDTO;
import com.wizeline.learningspring.repository.BankingAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankAccountServiceImplTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountServiceImplTest.class);
    @InjectMocks
    BankAccountServiceImpl service;
    @Mock
    MongoTemplate mongoTemplate;
    @Mock
    BankingAccountRepository repository;
    BankAccountDTO bankAccountDTO = new BankAccountDTO();
    UserDTO userDTO = new UserDTO();

    @BeforeEach
    void before() {
        userDTO.setUser("userTest");
        userDTO.setPassword("passwordTest");
        bankAccountDTO.setUserName("userNameTest");
        bankAccountDTO.setAccountNumber(1L);
        bankAccountDTO.setAccountType(AccountType.PLATINUM);
    }

    @Test
    void getAccountsNull() {
        LOGGER.info("getAccountsNullTest");
        List<BankAccountDTO> newBankAccountDTOS = new ArrayList<>();
        newBankAccountDTOS.add(bankAccountDTO);
        when(service.getAccounts()).thenReturn(null);
        when(mongoTemplate.findAll(BankAccountDTO.class)).thenReturn(newBankAccountDTOS);
        List<BankAccountDTO> bankAccountDTOS = service.getAccounts();
        assertNotNull(bankAccountDTOS);
        assertNotNull(newBankAccountDTOS);
    }

    @Test
    void getAccountsBankNull() {
        LOGGER.info("getAccountsBankNullTest");
        List<BankAccountDTO> newBankAccountDTOS = new ArrayList<>();
        newBankAccountDTOS.add(bankAccountDTO);
        lenient().when(service.getAccounts()).thenReturn(newBankAccountDTOS);
        lenient().when(mongoTemplate.findOne(any(), any())).thenReturn(null);
        List<BankAccountDTO> bankAccountDTOS = service.getAccounts();
        assertTrue(bankAccountDTOS.size() > 0);
    }

    @Test
    void deleteAccounts() {
        LOGGER.info("deleteAccountsTest");
        doNothing().when(repository).deleteAll();
        service.deleteAccounts();
    }

    @Test
    void updateAccount() {
        LOGGER.info("updateAccountTest");
        BankAccountDTO account = new BankAccountDTO(1L, "nameTest", "userNameTest", 123.22,
                AccountType.AHORRO, "countryTest", false, LocalDateTime.now(), LocalDateTime.now());
        BankAccountDTO accountUpdate = new BankAccountDTO(1L, "nameTestUpdate", "userNameTestUpdate", 123.22,
                AccountType.AHORRO, "countryTestUpdate", false, LocalDateTime.now(), LocalDateTime.now());
        when(repository.findByAccountNumber(1l)).thenReturn(Optional.of(account));
        service.updateAccount(1L, accountUpdate);
        assertNotEquals(account, accountUpdate);
    }

    @Test
    void getAccountByUser() {
        LOGGER.info("getAccountByUserTest");
        when(mongoTemplate.find(any(), any())).thenReturn(Collections.singletonList(bankAccountDTO));
        List<BankAccountDTO> bankAccountDTOS = service.getAccountByUser("userTest");
        assertTrue(bankAccountDTOS.size() > 0);
    }

    @Test
    void getAccountDetails() {
        LOGGER.info("getAccountDetailsTest");
        String date = "01-09-1989";
        BankAccountDTO bankAccountDTONew = service.getAccountDetails("userTest", date);
        assertNotNull(bankAccountDTONew);
    }

    @Test
    void getEncryptedAccount() {
        LOGGER.info("getEncryptedAccountTest");
        List<BankAccountDTO> newBankAccountDTOS = new ArrayList<>();
        newBankAccountDTOS.add(bankAccountDTO);
        when(service.encryptedAccounts()).thenReturn(newBankAccountDTOS);
        lenient().when(mongoTemplate.findOne(any(), any())).thenReturn(bankAccountDTO);
        lenient().when(mongoTemplate.findAll(BankAccountDTO.class)).thenReturn(newBankAccountDTOS);
        List<BankAccountDTO> bankAccountDTOS = service.encryptedAccounts();
        assertTrue(bankAccountDTOS.size() > 0);
    }

    public BankAccountDTO getUserByAccountNumber(Long accountNumber){
        return repository.findByAccountNumber(accountNumber).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("No encontrado")));
    }
}