package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.repository.BankingAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class BankAccountServiceImplTest {

    @Mock
    private BankAccountDTO bankAccountDTO;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private BankingAccountRepository bankAccountRepository;

    // Service -> interfaz principal
    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    // incializamos los mocks
    @BeforeEach
    void init(){
        MockitoAnnotations.openMocks(this);
        System.out.println("@BeforeEach => iniciando");
    }

    // Ejecutamos la logica de test
    @Test
    void getAccountsLocalTest(){
        System.out.println("@Test => getAccountsLocalTest()");
        List<BankAccountDTO> bankAccountDTOList = new ArrayList<BankAccountDTO>();
        bankAccountDTOList.add(bankAccountDTO);
        assertAll(
                () -> assertNotNull(bankAccountService.getAccountsLocal()),
                () -> assertEquals(bankAccountService.getAccountsLocal().size(), 3, "No se registraron las 3 cuentas")
        );
    }

    @Test
    void getAccountsTest(){
        System.out.println("@Test => getAccountsTest()");
        List<BankAccountDTO> bankAccountDTOList = new ArrayList<BankAccountDTO>();
        bankAccountDTOList.add(bankAccountDTO);
        when(mongoTemplate.save(bankAccountDTO)).thenReturn(bankAccountDTO);
        assertEquals(bankAccountService.getAccounts().size(), 3);
    }

    @Test
    void getAccountByUserTest(){
        System.out.println("@Test => getAccountByUserTest()");
        List<BankAccountDTO> bankAccountDTOList = new ArrayList<BankAccountDTO>();
        bankAccountDTOList.add(bankAccountDTO);
        when(mongoTemplate.find(any(Query.class), eq(BankAccountDTO.class))).thenReturn(bankAccountDTOList);
        assertNotNull(bankAccountService.getAccountByUser("usuario"));
        // assertEquals(bankAccountService.getAccountByUser("usuario").size(), 1);
    }

    @Test
    void getAccountDetailsTest(){
        System.out.println("@Test => getAccountDetailsTest()");
        assertNotNull(bankAccountService.getAccountDetails("user", "01-01-2022"));
    }

    @Test
    void deleteAccountsTest(){
        System.out.println("@Test => deleteAccountsTest()");
        doNothing().when(bankAccountRepository).deleteAll();
        bankAccountService.deleteAccounts();
    }

    @Test
    void changeCountryTest(){
        System.out.println("@Test => changeCountryTest()");
        when(mongoTemplate.findOne(any(Query.class), eq(BankAccountDTO.class))).thenReturn(bankAccountDTO);
        when(bankAccountRepository.save(bankAccountDTO)).thenReturn(bankAccountDTO);
        assertEquals(bankAccountService.changeCountry((long) 1.0, "MX").get(), bankAccountDTO);
    }

    @Test
    void changeCountryFalseTest(){
        System.out.println("@Test => changeCountryFalseTest()");
        when(mongoTemplate.findOne(any(Query.class), eq(BankAccountDTO.class))).thenReturn(null);
        assertEquals(bankAccountService.changeCountry((long) 1.0, "MX"), Optional.empty());
    }

    @Test
    void createAccountTest(){
        System.out.println("@Test => createAccountTest()");
        when(bankAccountRepository.save(bankAccountDTO)).thenReturn(bankAccountDTO);
        assertAll(
                () -> assertNotNull(bankAccountService.createAccount(bankAccountDTO))
        );
    }

}