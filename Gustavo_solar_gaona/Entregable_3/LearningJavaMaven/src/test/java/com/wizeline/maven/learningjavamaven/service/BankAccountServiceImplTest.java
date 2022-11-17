package com.wizeline.maven.learningjavamaven.service;

import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.repository.BankingAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

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
    void getAccountByUserTest(){
        System.out.println("@Test => getAccountByUserTest()");
        List<BankAccountDTO> bankAccountDTOList = new ArrayList<BankAccountDTO>();
        bankAccountDTOList.add(bankAccountDTO);
        when(mongoTemplate.find(any(Query.class), eq(BankAccountDTO.class))).thenReturn(bankAccountDTOList);
        assertNotNull(bankAccountService.getAccountByUser("usuario"));
        // assertEquals(bankAccountService.getAccountByUser("usuario").size(), 1);
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

}