package com.wizeline.maven.learningjavamaven.controller;


import com.wizeline.maven.learningjavamaven.model.BankAccountDTO;
import com.wizeline.maven.learningjavamaven.model.ResponseDTO;
import com.wizeline.maven.learningjavamaven.model.UserDTO;
import com.wizeline.maven.learningjavamaven.producer.KafkaProducer;
import com.wizeline.maven.learningjavamaven.service.BankAccountService;
import com.wizeline.maven.learningjavamaven.service.UserService;
import com.wizeline.maven.learningjavamaven.utils.CommonServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.wizeline.maven.learningjavamaven.enums.AccountType.AHORRO;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class) // prueba anotando los campos simulados
class UserControllerTest {
    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    //Usando REST Template
    @Mock
    private KafkaProducer producer;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private UserService userService;
    @Mock
    private BankAccountService bankAccountService;
    @Mock
    private CommonServices commonServices;
 

    @InjectMocks
    private UserController userController;

    @InjectMocks
    public UserDTO UserDTO;

    // probanado el endpoint @GetMapping("/login")
    @Test
    void loginUser() {
        // Arrange - prepara el escenario de tu prueba
        LOGGER.info("Entrando a realizar la preuba");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode("200");
        String user = "oswaldo";
        String password = "123";
        when(commonServices.login(user, password)).thenReturn(responseDTO);

        // Act - ejecuta la lógica a probar
        ResponseEntity<ResponseDTO> loginResponse = userController.loginUser(user, password);

        // Assert - valida resultados/llamadas
        assertTrue(loginResponse.getStatusCode().is2xxSuccessful(), "El código HTTP retornado no fue exitoso");
        assertTrue(loginResponse.hasBody(), "Response body no presente en respuesta");
        assertEquals("200", loginResponse.getBody().getCode(), "Código response dto no es 200");
        verify(commonServices, times(1)).login(user, password);
        LOGGER.info("Terminado de relizar la prueba . . . ");
    }

    @Test
    void createUser() {
        LOGGER.info("Entrando a realizar la prueba @PostMapping ");
        //Preparo el esenario para la prueba
        UserDTO userDTO = new UserDTO();
        userDTO.setUser("oswaldo");
        userDTO.setPassword("123");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode("201");
        when(userService.createUser(userDTO.getUser(), userDTO.getPassword())).thenReturn(responseDTO);

        //Ejecuta la logica a probar
        ResponseEntity<ResponseDTO> createUser = userController.createUser(userDTO);

        //valida resultados/llamadas
        assertTrue(createUser.getStatusCode().is2xxSuccessful(), "El codigo de respuesta fue exitoso");
        assertTrue(createUser.hasBody(), "Response body no presente en la respuesta. ");
        assertEquals("201", createUser.getBody().getCode(), "Codigo respuesta 201");
        LOGGER.info("Terminando de probar el metodo de createUser ");
    }

    //Pronabando el endpoint @GetMapping("/getUser")
    @Test
    void getUserAccount() {
        //Preparo el esenario de la prueba
        LOGGER.info("Entrando a realizar la prueba getUserAccount");
        ResponseDTO responseDTO = new ResponseDTO();
        BankAccountDTO accounts = new BankAccountDTO();
        accounts.setUserName("oswaldo");
        String user = "oswaldo";
        String date = "17-11-2022";
        when(bankAccountService.getAccountDetails(accounts.getUserName())).thenReturn(accounts);
        //Valida resultados y llamadas
        ResponseEntity count = userController.getUserAccount(user, date);//getUserAccount
        //Valido las llamadas
        assertTrue(count.getStatusCode().is2xxSuccessful(), "El codigo de respuesta fue exitoso");
        assertTrue(count.hasBody(), "Response body no presente en la respuesta. ");
        LOGGER.info("Terminando de realizar la prueba del metodo de getUserAccount . . . ");

    }


    @Test
    void createUsersAccount() {
    }

    @Test
    void getEncryip() {

        // Arrange - prepara el escenario de tu prueba
        LOGGER.info("Entrando a realizar de getEncryp");
        ResponseDTO responseDTO = new ResponseDTO();
        List<BankAccountDTO> accounts = bankAccountService.getAccounts();
        BankAccountDTO accoun = new BankAccountDTO();

        accoun.setAccountNumber(234);
        accoun.setAccountName("Pruebas");
        accoun.setUserName("ows");
        accoun.setAccountBalance(1.2);
        accoun.setAccountType(AHORRO);
        accoun.setCountry("mex");
        accoun.setAccountActive(true);

        when(bankAccountService.getAccounts()).thenReturn(accounts);
        // Act - ejecuta la lógica a probar
        ResponseEntity respuesta = userController.getEncryip();
        // Assert - valida resultados/llamadas
        assertTrue(respuesta.getStatusCode().is2xxSuccessful(), "El código HTTP retornado no fue exitoso");
        LOGGER.info("Terminando de realizar la pruba del metodo de getEncryip ");
    }

    @Test
    void testCreateUsersAccount() {
        LOGGER.info("Entrando a realizar la preuba");
        List<UserDTO> responseList = new ArrayList<>();

        ResponseEntity respuesta = userController.createUsersAccount(responseList);

        assertTrue(respuesta.getStatusCode().is2xxSuccessful(), "El codigo de respuesta fue exitoso");
    }

    @Test
    void createUserAccount() {
        LOGGER.info("Entrando a realizar la pruba de createUserAccount . . ");
        //Preparo el esenario de la prueba
        UserDTO userDTO = new UserDTO();
        userDTO.setUser("oswaldo");
        userDTO.setPassword("123");
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setCode("201");
        when(userService.createUser(userDTO.getUser(), userDTO.getPassword())).thenReturn(responseDTO);
        //Ejecuta la logica a probar createUsersAccount
        ResponseEntity<ResponseDTO> createUser = userController.createUserAccount(userDTO);

        //Valido las llamadas
        assertTrue(createUser.getStatusCode().is2xxSuccessful(), "El codigo de respuesta fue exitoso");
        assertTrue(createUser.hasBody(), "Response body no presente en la respuesta. ");
        //assertEquals("200", getUser.getBody(), "Codigo respuesta 200");
        LOGGER.info("Entrando a realizar la pruba de createUserAccount.");
    }

    @Test
    void run() {
    }

    @Test
    void splitQuery() {

    }

    @Test
    void getApi() {
        LOGGER.info("Entrando a realizar la pruba de getApi");
        Object obj = new Object();
        when(restTemplate.getForObject(anyString(), any())).thenReturn(obj);
        Object resul = userController.getApi();
        assertNotNull(resul);
        LOGGER.info("Terminado de realizar la pruba de getApi");
    }

    @Test
    void getUsers() {
        LOGGER.info("Entrando a realizar la prueba getUsers");
        ResponseEntity<?> createUser = userController.getUsers();
        assertTrue(createUser.getStatusCode().is2xxSuccessful(), "El codigo de respuesta fue exitoso");
        LOGGER.info("Terminando de realizar la pruba de getUsers");
    }



    @Test
    void kafkapro() {
        LOGGER.info("Entrando a realizar la pruba del metodo de kafkapro... ");
        ResponseDTO responseDTO = new ResponseDTO();
        ResponseEntity p = userController.kafkapro();
        assertTrue(p.getStatusCode().is2xxSuccessful(), "El codigo de respuesta fue exitoso");
        LOGGER.info("Terminando de realizar la prueba del metodo de kafkapro");

    }


}