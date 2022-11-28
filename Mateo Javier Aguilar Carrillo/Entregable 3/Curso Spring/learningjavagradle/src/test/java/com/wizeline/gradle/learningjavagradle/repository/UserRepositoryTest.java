package com.wizeline.gradle.learningjavagradle.repository;

import com.mongodb.client.result.UpdateResult;
import com.wizeline.gradle.learningjavagradle.model.RandomPassword;
import com.wizeline.gradle.learningjavagradle.model.ResponseDTO;
import com.wizeline.gradle.learningjavagradle.model.UserDTO;
import com.wizeline.gradle.learningjavagradle.service.UserServiceImpl;
import com.wizeline.gradle.learningjavagradle.singleton.RestTemplateConfig;
import com.wizeline.gradle.learningjavagradle.utils.EncryptorRSA;
import com.wizeline.gradle.learningjavagradle.utils.exceptions.ExcepcionGenerica;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.http.ResponseEntity;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import static com.wizeline.gradle.learningjavagradle.Datos.Datos.USER_001;
import static com.wizeline.gradle.learningjavagradle.Datos.Datos.USER_002;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRepositoryTest.class);

    @Mock
    MongoTemplate template;

    @InjectMocks
    UserRepositoryImpl userRepository;

    @Mock
    UserServiceImpl userService;

    @Mock
    EncryptorRSA rsa;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUserTest() {
        LOGGER.info("createUser Testing...");

        UserDTO userDTO = new UserDTO(USER_001.getUser(), USER_001.getPassword());

        when(template.save(any())).thenReturn(userDTO);

        assertNotNull(userRepository.createUser(USER_001.getUser(), USER_001.getPassword()));
    }

    @Test
    public void createUserWithRandomPasswordTest() throws NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeyException {
        LOGGER.info("createUserWithRandomPassword Testing...");
        RandomPassword randomPassword = RestTemplateConfig.getInstance().getRandomPassword();
        String passwordEncryptada = "";

        passwordEncryptada = rsa.encrypt(randomPassword.toString());

        UserDTO userDTO = new UserDTO(USER_001.getUser(), passwordEncryptada);
        when(template.save(any())).thenReturn(userDTO);

        assertNotNull(userRepository.createUser(USER_001.getUser()));
        assertEquals(userRepository.createUser(USER_001.getUser()), "success");
    }

    @Test
    public void loginFailedTest() {
        LOGGER.info("login Testing...");

        assertThrows(ExcepcionGenerica.class, () -> {
            assertNotNull(userRepository.login(USER_001.getUser(), USER_001.getPassword()));
        });
    }
}
