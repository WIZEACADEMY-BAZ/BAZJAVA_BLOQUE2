package com.baz.wizeline.learningspring.utils;

import com.baz.wizeline.learningspring.model.ResponseDTO;
import com.baz.wizeline.learningspring.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommonServicesTest {

    @Mock
    UserService userService;

    @InjectMocks
    CommonServices commonServices;

    ResponseDTO response;

    @BeforeEach
    void setUp() {
        response = new ResponseDTO();
        response.setStatus("success");
        response.setCode("OK000");

    }

    @Test
    void login() {

        when(userService.login(anyString(),anyString())).thenReturn(response);
        ResponseDTO responseDTO = commonServices.login("volcom", "Akali78@");
        assertNotNull(responseDTO);
    }

}
