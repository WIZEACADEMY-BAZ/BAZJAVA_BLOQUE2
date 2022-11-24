package com.wizeline.learningspring.controller;

import com.wizeline.learningspring.enums.AccountType;
import com.wizeline.learningspring.model.BankAccountDTO;
import com.wizeline.learningspring.model.ResponseDTO;
import com.wizeline.learningspring.service.BankAccountService;
import com.wizeline.learningspring.utils.CommonServices;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
class BankingAccountControllerTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(BankingAccountControllerTest.class);
    @Autowired
    BankingAccountController controller;
    @MockBean
    BankAccountService service;
    @MockBean
    CommonServices commonServices;

    @Test
    void sendUserAccountNull() {
        LOGGER.info("sendUserAccountNullTest");
        when(service.getAccounts()).thenReturn(null);
    }

    @Test
    void sendUserAccountEmpty() {
        LOGGER.info("sendUserAccountEmptyTest");
        when(service.getAccounts()).thenReturn(new ArrayList<>());
    }

    @Test
    void getUserAccount() {
        LOGGER.info("getUserAccountTest");
        ResponseDTO responseDTO= new ResponseDTO();
        responseDTO.setCode("OK000");
        BankAccountDTO account = new BankAccountDTO(1L, "nameTest", "userNameTest", 123.22,
                AccountType.AHORRO, "countryTest", false, LocalDateTime.now(), LocalDateTime.now());
        when(commonServices.login("userTest", "passwordTest")).thenReturn(responseDTO);
        when(service.getAccountDetails("userTest", "lastUsageTest")).thenReturn(account);
        assertAll(
                () -> assertNotNull(controller.getUserAccount("userTest", "passwordTest", "01-09-1989")),
                () -> assertEquals("OK000", responseDTO.getCode()),
                () -> assertEquals(controller.getUserAccount("userTest", "passwordTest", "01-09-1989").getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }

    @Test
    void getUserAccountCode() {
        LOGGER.info("getUserAccountCodeTest");
        ResponseDTO responseDTO= new ResponseDTO();
        responseDTO.setCode("OK001");
        when(commonServices.login("userTest", "passwordTest")).thenReturn(responseDTO);
        assertAll(
                () -> assertNotNull(controller.getUserAccount("userTest", "passwordTest", "01-09-1989")),
                () -> assertEquals("OK001", responseDTO.getCode()),
                () -> assertEquals(controller.getUserAccount("userTest", "passwordTest", "01-09-1989").getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }

    @Test
    void getUserAccountPassInvalid() {
        LOGGER.info("getUserAccountPassInvalidTest");
        ResponseDTO responseDTO= new ResponseDTO();
        responseDTO.setCode("OK001");
        assertAll(
                () -> assertNotNull(controller.getUserAccount("userTest", "passwordTest", "01-09-1989")),
                () -> assertEquals("OK001", responseDTO.getCode()),
                () -> assertEquals(controller.getUserAccount("userTest", "passwordTest", "01-09-1989").getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }

    @Test
    void getUserAccountDateInvalid() {
        LOGGER.info("getUserAccountDateInvalidTest");
        ResponseDTO responseDTO= new ResponseDTO();
        responseDTO.setCode("OK001");
        assertAll(
                () -> assertNotNull(controller.getUserAccount("userTest", "passwordTest", "01-09-1989")),
                () -> assertEquals("OK001", responseDTO.getCode()),
                () -> assertEquals(controller.getUserAccount("userTest", "passwordTest", "01-09-1989").getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }

    @Test
    void getAccounts() {
        LOGGER.info("getAccountsTest");
        List<BankAccountDTO> accounts = List.of(new BankAccountDTO(1L, "nameTest1", "userNameTest1", 123.22,
                        AccountType.AHORRO, "countryTest1", false, LocalDateTime.now(), LocalDateTime.now()),
                new BankAccountDTO(2L, "nameTest2", "userNameTest2", 123.22,
                        AccountType.NOMINA, "countryTest2", false, LocalDateTime.now(), LocalDateTime.now())
        );
        Instant inicioDeEjecucion = Instant.now();
        Instant finalDeEjecucion = Instant.now();
        String total = (String.valueOf(Duration.between(inicioDeEjecucion,
                finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        when(service.getAccounts()).thenReturn(accounts);

        assertAll(
                () -> assertNotNull(controller.getAccounts()),
                () -> assertEquals(controller.getAccounts().getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }

    @Test
    void getAccountByUser() {
        LOGGER.info("getAccountByUserTest");
        List<BankAccountDTO> accounts = List.of(new BankAccountDTO(1L, "nameTest1", "userNameTest1", 123.22,
                        AccountType.AHORRO, "countryTest1", false, LocalDateTime.now(), LocalDateTime.now()),
                new BankAccountDTO(2L, "nameTest2", "userNameTest2", 123.22,
                        AccountType.NOMINA, "countryTest2", false, LocalDateTime.now(), LocalDateTime.now())
        );
        Instant inicioDeEjecucion = Instant.now();
        Instant finalDeEjecucion = Instant.now();
        String total = (String.valueOf(Duration.between(inicioDeEjecucion,
                finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));

        when(service.getAccounts()).thenReturn(accounts);

        assertAll(
                () -> assertNotNull(controller.getAccountByUser("userNameTest2")),
                () -> assertEquals(controller.getAccounts().getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }

    @Test
    void getAccountsGroupByType() {
        LOGGER.info("getAccountsGroupByTypeTest");
        List<BankAccountDTO> accounts = List.of(new BankAccountDTO(1L, "nameTest1", "userNameTest1", 123.22,
                        AccountType.AHORRO, "countryTest1", false, LocalDateTime.now(), LocalDateTime.now()),
                new BankAccountDTO(2L, "nameTest2", "userNameTest2", 123.22,
                        AccountType.NOMINA, "countryTest2", false, LocalDateTime.now(), LocalDateTime.now())
        );
        Instant inicioDeEjecucion = Instant.now();
        Instant finalDeEjecucion = Instant.now();
        String total = (String.valueOf(Duration.between(inicioDeEjecucion,
                finalDeEjecucion).toMillis()).concat(" segundos."));
        LOGGER.info("Tiempo de respuesta: ".concat(total));
        Map<String, List<BankAccountDTO>> groupedAccounts;
        Function<BankAccountDTO, String> groupFunction = (account) -> account.getAccountType().toString();
        groupedAccounts = accounts.stream().collect(Collectors.groupingBy(groupFunction));

        when(service.getAccounts()).thenReturn(accounts);

        assertAll(
                () -> assertNotNull(controller.getAccountsGroupByType()),
                () -> assertEquals(controller.getAccountsGroupByType().getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }

    @Test
    void sayHelloGuest() {
        LOGGER.info("Test");
        assertAll(
                () -> assertNotNull(controller.sayHelloGuest()),
                () -> assertEquals(controller.sayHelloGuest().getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }

    @Test
    void deleteAccounts() {
        LOGGER.info("deleteAccountsTest");
        doNothing().when(service).deleteAccounts();
        ResponseEntity<String> response = controller.deleteAccounts();
        assertAll(
                () -> assertNotNull(response),
                () -> assertNotNull(controller.deleteAccounts()),
                () -> assertEquals(controller.deleteAccounts().getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }

    @Test
    void updateAccount() {
        LOGGER.info("updateAccountTest");
        BankAccountDTO account = new BankAccountDTO(1L, "nameTest", "userNameTest", 123.22,
                        AccountType.AHORRO, "countryTest", false, LocalDateTime.now(), LocalDateTime.now());
        BankAccountDTO accountUpdate = new BankAccountDTO(1L, "nameTestUpdate", "userNameTestUpdate", 123.22,
                AccountType.AHORRO, "countryTestUpdate", false, LocalDateTime.now(), LocalDateTime.now());

        ResponseEntity<?> response = controller.updateAccount(1L, accountUpdate);
        assertAll(
                () -> assertNotNull(response),
                () -> assertNotNull(controller.updateAccount(1L, accountUpdate)),
                () -> assertNotEquals(account, accountUpdate),
                () -> assertEquals(controller.updateAccount(1L, accountUpdate).getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }

    @Test
    void encryptedAccounts() {
        LOGGER.info("encryptedAccountsTest");
        Instant inicioDeEjecucion = Instant.now();
        List<BankAccountDTO> accounts = List.of(new BankAccountDTO(1L, "nameTest1", "userNameTest1", 123.22,
                        AccountType.AHORRO, "countryTest1", false, LocalDateTime.now(), LocalDateTime.now()),
                new BankAccountDTO(2L, "nameTest2", "userNameTest2", 123.22,
                        AccountType.NOMINA, "countryTest2", false, LocalDateTime.now(), LocalDateTime.now())
        );
        when(service.encryptedAccounts()).thenReturn(accounts);
        Instant finalDeEjecucion = Instant.now();
        String total = String.valueOf(Duration.between(inicioDeEjecucion,
                finalDeEjecucion).toMillis()).concat(" segundos.");
        LOGGER.info("Tiempo de respuesta: ".concat(total));
        assertAll(
                () -> assertNotNull(accounts),
                () -> assertNotNull(controller.encryptedAccounts()),
                () -> assertEquals(controller.encryptedAccounts().getStatusCode().value(),
                        HttpStatus.OK.value())
        );
    }
}