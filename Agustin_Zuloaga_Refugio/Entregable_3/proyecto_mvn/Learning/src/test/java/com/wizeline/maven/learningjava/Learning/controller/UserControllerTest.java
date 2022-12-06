package com.wizeline.maven.learningjava.Learning.controller;

import com.wizeline.maven.learningjava.Learning.model.ResponseDTO;
import com.wizeline.maven.learningjava.Learning.model.UserDateDTO;
import com.wizeline.maven.learningjava.Learning.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @InjectMocks
    UserController userController;
    @Mock
    UserService userService;
    ResponseDTO responseDTO = new ResponseDTO();

    @BeforeEach
    void before() {
        responseDTO.setStatus("correcto");
        responseDTO.setCode("OK000");
    }

    @Test
    @DisplayName("Procesos de consumo de usuarios")
    void getUsers() {
        ResponseEntity<String> response = userController.getUsers();
        assertNotNull(response);
    }
    @Test
    void userDate() {
        UserDateDTO userDateDTO = new UserDateDTO();
        userDateDTO.setUser("1");
        userDateDTO.setAnio("12");
        userDateDTO.setFechaNacimiento("12-01-2010");
        List<UserDateDTO> userDateDTOList = Collections.singletonList(userDateDTO);
        when(userService.getUserDateIterator("1")).thenReturn(userDateDTOList);
        ResponseEntity<List<UserDateDTO>> response = userController.userDate("1");
        assertNotNull(response);
    }

}