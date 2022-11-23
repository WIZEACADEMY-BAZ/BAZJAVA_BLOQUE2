package com.wizeline.entregabledavid.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.wizeline.entregabledavid.enums.AccountType;
import com.wizeline.entregabledavid.model.BankAccountDTO;
import com.wizeline.entregabledavid.repository.BankingAccountRepository;

import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BankAccountServiceImpl.class})
@ExtendWith(SpringExtension.class)
class BankAccountServiceImplTest {
    @Autowired
    private BankAccountServiceImpl bankAccountServiceImpl;

    @MockBean
    private BankingAccountRepository bankingAccountRepository;

    @MockBean
    private MongoTemplate mongoTemplate;

    /**
     * Metodo Test: {@link BankAccountServiceImpl#getAccounts()}
     */
    @Test
    void testGetAccounts() {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountActive(true);
        bankAccountDTO.setAccountBalance(10.0d);
        bankAccountDTO.setAccountBalanceCifrado("3");
        bankAccountDTO.setAccountName("Juan");
        bankAccountDTO.setAccountNumber(1234567890L);
        bankAccountDTO.setAccountType(AccountType.NOMINA);
        bankAccountDTO.setCountry("US");
        bankAccountDTO.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bankAccountDTO.setLastUsage(LocalDateTime.of(1, 1, 1, 1, 1));
        bankAccountDTO.setUser("User");
        when(mongoTemplate.save((BankAccountDTO) any())).thenReturn(bankAccountDTO);
        when(mongoTemplate.findAll((Class<BankAccountDTO>) any())).thenReturn(new ArrayList<>());
        assertEquals(3, bankAccountServiceImpl.getAccounts().size());
        verify(mongoTemplate, atLeast(1)).save((BankAccountDTO) any());
        verify(mongoTemplate).findAll((Class<BankAccountDTO>) any());
    }

    /**
     * Metodo Test: {@link BankAccountServiceImpl#getAccounts()}
     */
    @Test
    void testGetAccounts2() {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountActive(true);
        bankAccountDTO.setAccountBalance(10.0d);
        bankAccountDTO.setAccountBalanceCifrado("3");
        bankAccountDTO.setAccountName("Juan");
        bankAccountDTO.setAccountNumber(1234567890L);
        bankAccountDTO.setAccountType(AccountType.NOMINA);
        bankAccountDTO.setCountry("US");
        bankAccountDTO.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bankAccountDTO.setLastUsage(LocalDateTime.of(1, 1, 1, 1, 1));
        bankAccountDTO.setUser("User");

        BankAccountDTO bankAccountDTO1 = new BankAccountDTO();
        bankAccountDTO1.setAccountActive(true);
        bankAccountDTO1.setAccountBalance(1000.0d);
        bankAccountDTO1.setAccountBalanceCifrado("3");
        bankAccountDTO1.setAccountName("Juan");
        bankAccountDTO1.setAccountNumber(1234567890L);
        bankAccountDTO1.setAccountType(AccountType.NOMINA);
        bankAccountDTO1.setCountry("US");
        bankAccountDTO1.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bankAccountDTO1.setLastUsage(LocalDateTime.of(1, 1, 1, 1, 1));
        bankAccountDTO1.setUser("user3@wizeline.com");

        ArrayList<BankAccountDTO> bankAccountDTOList = new ArrayList<>();
        bankAccountDTOList.add(bankAccountDTO1);
        when(mongoTemplate.save((BankAccountDTO) any())).thenReturn(bankAccountDTO);
        when(mongoTemplate.findAll((Class<BankAccountDTO>) any())).thenReturn(bankAccountDTOList);
        assertEquals(3, bankAccountServiceImpl.getAccounts().size());
        verify(mongoTemplate, atLeast(1)).save((BankAccountDTO) any());
        verify(mongoTemplate).findAll((Class<BankAccountDTO>) any());
    }

    /**
     * Metodo Test: {@link BankAccountServiceImpl#getAccounts()}
     */
    @Test
    void testGetAccounts3() {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountActive(true);
        bankAccountDTO.setAccountBalance(10.0d);
        bankAccountDTO.setAccountBalanceCifrado("3");
        bankAccountDTO.setAccountName("Juan");
        bankAccountDTO.setAccountNumber(1234567890L);
        bankAccountDTO.setAccountType(AccountType.NOMINA);
        bankAccountDTO.setCountry("US");
        bankAccountDTO.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bankAccountDTO.setLastUsage(LocalDateTime.of(1, 1, 1, 1, 1));
        bankAccountDTO.setUser("User");

        BankAccountDTO bankAccountDTO1 = new BankAccountDTO();
        bankAccountDTO1.setAccountActive(true);
        bankAccountDTO1.setAccountBalance(1000.0d);
        bankAccountDTO1.setAccountBalanceCifrado("3");
        bankAccountDTO1.setAccountName("Juan");
        bankAccountDTO1.setAccountNumber(1234567890L);
        bankAccountDTO1.setAccountType(AccountType.NOMINA);
        bankAccountDTO1.setCountry("US");
        bankAccountDTO1.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bankAccountDTO1.setLastUsage(LocalDateTime.of(1, 1, 1, 1, 1));
        bankAccountDTO1.setUser("user3@wizeline.com");

        BankAccountDTO bankAccountDTO2 = new BankAccountDTO();
        bankAccountDTO2.setAccountActive(true);
        bankAccountDTO2.setAccountBalance(1000.0d);
        bankAccountDTO2.setAccountBalanceCifrado("3");
        bankAccountDTO2.setAccountName("Juan");
        bankAccountDTO2.setAccountNumber(1234567890L);
        bankAccountDTO2.setAccountType(AccountType.NOMINA);
        bankAccountDTO2.setCountry("US");
        bankAccountDTO2.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bankAccountDTO2.setLastUsage(LocalDateTime.of(1, 1, 1, 1, 1));
        bankAccountDTO2.setUser("user3@wizeline.com");

        ArrayList<BankAccountDTO> bankAccountDTOList = new ArrayList<>();
        bankAccountDTOList.add(bankAccountDTO2);
        bankAccountDTOList.add(bankAccountDTO1);
        when(mongoTemplate.save((BankAccountDTO) any())).thenReturn(bankAccountDTO);
        when(mongoTemplate.findAll((Class<BankAccountDTO>) any())).thenReturn(bankAccountDTOList);
        assertEquals(3, bankAccountServiceImpl.getAccounts().size());
        verify(mongoTemplate, atLeast(1)).save((BankAccountDTO) any());
        verify(mongoTemplate).findAll((Class<BankAccountDTO>) any());
    }


    /**
     * Metodo Test: {@link BankAccountServiceImpl#deleteAccounts()}
     */
    @Test
    void testDeleteAccounts() {
        doNothing().when(bankingAccountRepository).deleteAll();
        bankAccountServiceImpl.deleteAccounts();
        verify(bankingAccountRepository).deleteAll();
        assertEquals(3, bankAccountServiceImpl.getAccounts().size());
    }

    /**
     * Metodo Test: {@link BankAccountServiceImpl#getAccountByUser(String)}
     */
    @Test
    void testGetAccountByUser() {
        ArrayList<BankAccountDTO> bankAccountDTOList = new ArrayList<>();
        when(mongoTemplate.find((Query) any(), (Class<BankAccountDTO>) any())).thenReturn(bankAccountDTOList);
        List<BankAccountDTO> actualAccountByUser = bankAccountServiceImpl.getAccountByUser("User");
        assertSame(bankAccountDTOList, actualAccountByUser);
        assertTrue(actualAccountByUser.isEmpty());
        verify(mongoTemplate).find((Query) any(), (Class<BankAccountDTO>) any());
    }

    /**
     * Metodo Test: {@link BankAccountServiceImpl#putAccountByUser(String)}
     */
    @Test
    void testPutAccountByUser() {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setAccountActive(true);
        bankAccountDTO.setAccountBalance(10.0d);
        bankAccountDTO.setAccountBalanceCifrado("3");
        bankAccountDTO.setAccountName("Juan");
        bankAccountDTO.setAccountNumber(1234567890L);
        bankAccountDTO.setAccountType(AccountType.NOMINA);
        bankAccountDTO.setCountry("US");
        bankAccountDTO.setCreationDate(LocalDateTime.of(1, 1, 1, 1, 1));
        bankAccountDTO.setLastUsage(LocalDateTime.of(1, 1, 1, 1, 1));
        bankAccountDTO.setUser("User");
        when(mongoTemplate.updateFirst((Query) any(), (UpdateDefinition) any(), (Class<Object>) any())).thenReturn(null);
        when(mongoTemplate.findOne((Query) any(), (Class<BankAccountDTO>) any())).thenReturn(bankAccountDTO);
        assertSame(bankAccountDTO, bankAccountServiceImpl.putAccountByUser("User"));
        verify(mongoTemplate).updateFirst((Query) any(), (UpdateDefinition) any(), (Class<Object>) any());
        verify(mongoTemplate, atLeast(1)).findOne((Query) any(), (Class<BankAccountDTO>) any());
    }
}

