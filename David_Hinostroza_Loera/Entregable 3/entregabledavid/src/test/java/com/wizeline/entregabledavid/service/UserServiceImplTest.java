package com.wizeline.entregabledavid.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.wizeline.entregabledavid.builder.User;
import com.wizeline.entregabledavid.iterator.UserDTOCollectionImpl;
import com.wizeline.entregabledavid.model.ErrorDTO;
import com.wizeline.entregabledavid.model.ResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserServiceImpl.class})
@ExtendWith(SpringExtension.class)
class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * Metodo test: {@link UserServiceImpl#createUser(String, String)}
     */
    @Test
    void testCreateUser() {

        ResponseDTO actualCreateUserResult = (new UserServiceImpl()).createUser("User", "password");
        assertEquals("OK000", actualCreateUserResult.getCode());
        assertEquals("success", actualCreateUserResult.getStatus());
        ErrorDTO errors = actualCreateUserResult.getErrors();
        assertEquals(null, errors.getErrorCode());
        assertEquals(null, errors.getMessage());

    }

    /**
     * Metodo Test: {@link UserServiceImpl#createUser(String, String)}
     */
    @Test
    void testCreateUser2() {

        ResponseDTO actualCreateUserResult = (new UserServiceImpl()).createUser(null, null);
        assertEquals("OK000", actualCreateUserResult.getCode());
        assertEquals("fail", actualCreateUserResult.getStatus());
        ErrorDTO errors = actualCreateUserResult.getErrors();
        assertEquals("ER001", errors.getErrorCode());
        assertEquals("Error al crear usuario", errors.getMessage());
    }

    /**
     * Metodo Test: {@link UserServiceImpl#createUser(String, String)}
     */
    @Test
    void testCreateUser3() {

        ResponseDTO actualCreateUserResult = (new UserServiceImpl()).createUser("user",null);
        assertEquals("OK000", actualCreateUserResult.getCode());
        assertEquals("fail", actualCreateUserResult.getStatus());
        ErrorDTO errors = actualCreateUserResult.getErrors();
        assertEquals("ER001", errors.getErrorCode());
        assertEquals("Error al crear usuario", errors.getMessage());
    }

    /**
     * Metodo Test: {@link UserServiceImpl#login(String, String)}
     */
    @Test
    void testLogin() {

        ResponseDTO actualLoginResult = (new UserServiceImpl()).login("User", "password");
        assertEquals("OK000", actualLoginResult.getCode());
        assertEquals("success", actualLoginResult.getStatus());
        ErrorDTO errors = actualLoginResult.getErrors();
        assertEquals(null, errors.getErrorCode());
        assertEquals(null, errors.getMessage());

    }

    /**
     * Metodo Test: {@link UserServiceImpl#login(String, String)}
     */
    @Test
    void testLogin2() {

        ResponseDTO actualLoginResult = (new UserServiceImpl()).login(null, null);
        assertEquals("ER001", actualLoginResult.getCode());
        assertEquals("fail", actualLoginResult.getStatus());
        ErrorDTO errors = actualLoginResult.getErrors();
        assertEquals("ER001", errors.getErrorCode());
        assertEquals("", errors.getMessage());
    }

    /**
     * Metodo Test: {@link UserServiceImpl#login(String, String)}
     */
    @Test
    void testLogin3() {

        ResponseDTO actualLoginResult = (new UserServiceImpl()).login("user", null);
        assertEquals("ER001", actualLoginResult.getCode());
        assertEquals("fail", actualLoginResult.getStatus());
        ErrorDTO errors = actualLoginResult.getErrors();
        assertEquals("ER001", errors.getErrorCode());
        assertEquals("", errors.getMessage());
    }

    /**
     * Metodo Test: {@link UserServiceImpl#getUserBuild()}
     */
    @Test
    void testGetUserBuild() {
        User actualUserBuild = userServiceImpl.getUserBuild();
        assertEquals("Fake address 1234", actualUserBuild.getAddress());
        assertEquals("1234567", actualUserBuild.getPhone());
        assertEquals("Doe", actualUserBuild.getLastName());
        assertEquals("Jane", actualUserBuild.getFirstName());
        assertEquals(30, actualUserBuild.getAge());
    }

    /**
     * Metodo Test: {@link UserServiceImpl#populateUsers()}
     */
    @Test
    void testPopulateUsers() {
        assertTrue(userServiceImpl.populateUsers() instanceof UserDTOCollectionImpl);
    }
}

