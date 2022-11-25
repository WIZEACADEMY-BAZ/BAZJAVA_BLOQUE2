package baz.practice.wizeline.learningjavamaven.service;

import baz.practice.wizeline.learningjavamaven.model.ResponseDTO;
import baz.practice.wizeline.learningjavamaven.repository.UserDAO;
import baz.practice.wizeline.learningjavamaven.utils.Utils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserBOImplTest {

    public static final Logger LOGGER = Logger.getLogger(UserBOImplTest.class.getName());
    @InjectMocks
    UserBOImpl userBO;

    @Mock
    UserDAO userDAO;

    @Test
    void createUserTest() {
        LOGGER.info("Se inicializa proceso TEST de createUserTest...");
        ResponseDTO response = userBO.createUser("user", "pass");
        assertEquals("OK000",response.getCode());
    }

    @Test
    void createUserTestFail() {
        LOGGER.info("Se inicializa proceso TEST de createUserTestFail...");
        ResponseDTO response = userBO.createUser(null, null);
        assertEquals("ER001",response.getCode());
    }

    @Test
    void login() {
        LOGGER.info("Se inicializa proceso TEST de login...");
        lenient().when(userDAO.login(anyString(),anyString())).thenReturn("success");
        ResponseDTO responseDTO = userBO.login("user", "pass");
        assertEquals(responseDTO.getStatus(),"success");
    }
    @Test
    void loginFail() {
        LOGGER.info("Se inicializa proceso TEST de loginFail...");
        lenient().when(userDAO.login(anyString(),anyString())).thenReturn("fail");
        ResponseDTO responseDTO = userBO.login(null, null);
        assertEquals(responseDTO.getStatus(),"fail");
    }
}