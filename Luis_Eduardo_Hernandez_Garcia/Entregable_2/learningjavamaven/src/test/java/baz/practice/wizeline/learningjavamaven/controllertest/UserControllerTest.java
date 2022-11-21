package baz.practice.wizeline.learningjavamaven.controllertest;

import baz.practice.wizeline.learningjavamaven.controller.UserController;
import baz.practice.wizeline.learningjavamaven.model.ResponseDTO;
import baz.practice.wizeline.learningjavamaven.model.UserDTO;
import baz.practice.wizeline.learningjavamaven.service.UserBO;
import static org.junit.jupiter.api.Assertions.*;

import io.github.bucket4j.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.http.WebSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    UserBO userBO;

    @InjectMocks
    UserController userController;

    @Test
    public void loginTest(){
        String user = "Megaman", pass = "123";
        when(userBO.login(user, pass)).thenReturn(new ResponseDTO());

        assertEquals(userController.login(user,pass).getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void createUserTest(){
        UserDTO objeto = new UserDTO();

        objeto.setUser("User1");
        objeto.setPassword("Pass1");

        when(userBO.createUser(objeto.getUser(), objeto.getPassword())).thenReturn(new ResponseDTO());

        assertEquals(userController.createUser(objeto).getStatusCode(),HttpStatus.OK);
    }

    @Test
    public void createUsersAccountTest(){
        List<UserDTO> userDTOList = new ArrayList<>();

        lenient().when(userBO.createUser(anyString(), anyString())).thenReturn(new ResponseDTO());

        ResponseEntity<List<ResponseDTO>> response = userController.createUsersAccount(userDTOList);
        assertEquals(response.getStatusCode(),HttpStatus.OK);
    }

    @Test
    public void createUserUnicTest(){
        ResponseDTO response = new ResponseDTO();
        response.setCode("OK000");

        UserDTO user = new UserDTO();

        user.setUser("User1");
        user.setPassword("Pass1");

        lenient().when(userBO.createUser(user.getUser(), user.getPassword())).thenReturn(response);

        assertEquals(userController.createUserUnic(user).getStatusCode(),HttpStatus.OK);
    }

    @Test
    public void getUsersTest(){
        final Bucket bucket;

        ResponseDTO response = new ResponseDTO();
        response.setCode("OK000");

        lenient().when(userBO.login(anyString(),anyString())).thenReturn(response);

        ResponseEntity<?> responseFinal = userController.getUsers(anyString(),anyString());

        assertEquals(responseFinal.getStatusCode(),HttpStatus.OK);

    }
/*
    @Test
    public void getUsersTooManyTest(){
        final Bucket bucket =;

        ResponseDTO response = new ResponseDTO();
        response.setCode("OK000");

        lenient().when(bucket.asBlocking()).thenReturn(bucket.asBlocking());

        ResponseEntity<?> responseFinal = userController.getUsers(anyString(),anyString());

        assertEquals(responseFinal.getStatusCode(),HttpStatus.TOO_MANY_REQUESTS);
    }

 */
}
