package com.wizeline.learningjavamaven.utils;

import com.wizeline.learningjavamaven.model.ResponseDTO;
import com.wizeline.learningjavamaven.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommonServicesTest {

  @InjectMocks
  CommonServices commonServices;

  @Mock
  UserService userService;

  @Test
  void login() {
    ResponseDTO response = new ResponseDTO();
    response.setStatus("okk");
    response.setCode("200");
    when(userService.login(Mockito.anyString(), Mockito.anyString())).thenReturn(response);
    ResponseDTO responseDTO = commonServices.login("alex", "pass");
    assertNotNull(responseDTO);
  }
}