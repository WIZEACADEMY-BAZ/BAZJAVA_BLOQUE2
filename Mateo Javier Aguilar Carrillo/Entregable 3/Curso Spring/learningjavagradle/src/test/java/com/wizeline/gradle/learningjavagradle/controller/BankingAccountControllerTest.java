package com.wizeline.gradle.learningjavagradle.controller;

import com.wizeline.gradle.learningjavagradle.enums.AccountType;
import com.wizeline.gradle.learningjavagradle.enums.Country;
import com.wizeline.gradle.learningjavagradle.model.BankAccountDTO;
import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import com.wizeline.gradle.learningjavagradle.service.BankAccountService;
import com.wizeline.gradle.learningjavagradle.utils.CommonServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.mockito.Mockito.lenient;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class BankingAccountControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankingAccountControllerTest.class);
    private final String USER_TEST = "userTest";
    private final String PASS_TEST = "Thenew4@";
    private final String DATE_TEST = "01-01-1980";

    @InjectMocks
    BankingAccountController bankingAccountController;

    @Mock
    BankAccountService bankAccountService;

    @MockBean
    CommonServices commonServices;

    @Test
    public void getUserAccountTest() {
        LOGGER.info("getUserAccount Testing...");
        ResponseDTO responseDTO = new ResponseDTO(null, "OK000", null);

        BankAccountDTO bankAccountDTO = new BankAccountDTO(1L, "Nomina", USER_TEST, 100.10, AccountType.NOMINA, Country.MX.toString(),
                true, LocalDateTime.now(), LocalDateTime.now());

        lenient().when(commonServices.login(USER_TEST, PASS_TEST)).thenReturn(responseDTO);
        lenient().when(bankAccountService.getAccountDetails(USER_TEST, PASS_TEST)).thenReturn(bankAccountDTO);
        assertNotNull(bankingAccountController.getUserAccount(USER_TEST, PASS_TEST, DATE_TEST));
        assertEquals(responseDTO.getCode(), "OK000");
        assertEquals(bankingAccountController.getUserAccount(USER_TEST,PASS_TEST,DATE_TEST).getStatusCode().value(), HttpStatus.OK.value());
    }

    @Test
    public void getUserAccountNotMatcherPasswordTest() {
        LOGGER.info("getUserAccountNotMatcherPassword Testing...");
        ResponseDTO responseDTO = new ResponseDTO(null, "OK000", null);

        BankAccountDTO bankAccountDTO = new BankAccountDTO(1L, "Nomina", USER_TEST, 100.10, AccountType.NOMINA, Country.MX.toString(),
                true, LocalDateTime.now(), LocalDateTime.now());

        lenient().when(commonServices.login(USER_TEST, PASS_TEST)).thenReturn(responseDTO);
        lenient().when(bankAccountService.getAccountDetails(USER_TEST, "PASS_TEST")).thenReturn(bankAccountDTO);
        assertNotNull(bankingAccountController.getUserAccount(USER_TEST, "PASS_TEST", DATE_TEST));
        assertEquals(bankingAccountController.getUserAccount(USER_TEST,"PASS_TEST",DATE_TEST).getBody(), "Password Incorrecto");
    }

    @Test
    public void getUserAccountFailedTest() {
        LOGGER.info("getUserAccountFailed Testing...");
        ResponseDTO responseDTO = new ResponseDTO(null, "OK000", null);

        BankAccountDTO bankAccountDTO = new BankAccountDTO(1L, "Nomina", USER_TEST, 100.10, AccountType.NOMINA, Country.MX.toString(),
                true, LocalDateTime.now(), LocalDateTime.now());

        lenient().when(commonServices.login(USER_TEST, PASS_TEST)).thenReturn(responseDTO);
        lenient().when(bankAccountService.getAccountDetails(USER_TEST, "PASS_TEST")).thenReturn(bankAccountDTO);
        assertNotNull(bankingAccountController.getUserAccount(USER_TEST, "PASS_TEST", "fecha invalida"));
        assertEquals(bankingAccountController.getUserAccount(USER_TEST,"PASS_TEST","fecha invalida").getBody(), "Formato de Fecha Incorrecto");
    }

    @Test
    public void getAccountsTest() {
        LOGGER.info("getAccounts Testing...");
        List<BankAccountDTO> accountDTOList = List.of(new BankAccountDTO(1L, "Nomina", USER_TEST, 100.10, AccountType.NOMINA, Country.MX.toString(),
                true, LocalDateTime.now(), LocalDateTime.now()), new BankAccountDTO(2L, "Ahorro", USER_TEST, 10000.10,
                AccountType.AHORRO, Country.MX.toString(),true, LocalDateTime.now(), LocalDateTime.now()));
        lenient().when(bankAccountService.getAccounts()).thenReturn(accountDTOList);

        assertNotNull(bankingAccountController.getAccounts());
        assertEquals(bankingAccountController.getAccounts().getStatusCode().value(), HttpStatus.OK.value());
    }

    @Test
    public void getAccountByUserTest() {
        LOGGER.info("getAccountByUser Testing...");

        List<BankAccountDTO> accountDTOList = List.of( new BankAccountDTO(1L, "Nomina", USER_TEST, 100.10, AccountType.NOMINA, Country.MX.toString(),
                true, LocalDateTime.now(), LocalDateTime.now()), new BankAccountDTO(2L, "Ahorro", USER_TEST, 10000.10,
                AccountType.AHORRO, Country.MX.toString(),true, LocalDateTime.now(), LocalDateTime.now()));

        lenient().when(bankAccountService.getAccounts()).thenReturn(accountDTOList);

        assertNotNull(bankingAccountController.getAccountByUser(USER_TEST));
        assertEquals(bankingAccountController.getAccounts().getStatusCode().value(), HttpStatus.OK.value());
    }

    @Test
    public void getAccountByGroupTypeTest() {
        LOGGER.info("getAccountByGroupType Testing...");

        List<BankAccountDTO> accountDTOList = List.of( new BankAccountDTO(1L, "Nomina", USER_TEST, 100.10, AccountType.NOMINA, Country.MX.toString(),
                true, LocalDateTime.now(), LocalDateTime.now()), new BankAccountDTO(2L, "Ahorro", USER_TEST, 10000.10,
                AccountType.AHORRO, Country.MX.toString(),true, LocalDateTime.now(), LocalDateTime.now()));

        lenient().when(bankAccountService.getAccounts()).thenReturn(accountDTOList);

        Map<String, List<BankAccountDTO>> groupedAccounts;
        Function<BankAccountDTO, String> function = (account) -> account.getAccountType().toString();
        groupedAccounts = accountDTOList.stream().collect(Collectors.groupingBy(function));

        assertAll(
                () -> assertNotNull(groupedAccounts),
                () -> assertNotNull(bankingAccountController.getAccountsGroupByType()),
                () -> assertEquals(bankingAccountController.getAccountsGroupByType().getStatusCode().value(), HttpStatus.OK.value())
        );
    }

    @Test
    public void deleteAccountsTest() {
        LOGGER.info("deleteAccounts Testing...");

        assertNotNull(bankingAccountController.deleteAccounts());
        assertEquals(bankingAccountController.deleteAccounts().getStatusCode().value(), HttpStatus.OK.value());
    }

    @Test
    public void sayHelloTest() {
        LOGGER.info("sayHello Testing...");
        assertNotNull(bankingAccountController.sayHelloGuest());
        assertEquals(bankingAccountController.sayHelloGuest().getStatusCode().value(), HttpStatus.OK.value());
    }
}
