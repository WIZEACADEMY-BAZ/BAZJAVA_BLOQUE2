package com.wizeline.learningjavamaven.controller;

import com.wizeline.learningjavamaven.model.detalle.UserDescription;
import com.wizeline.learningjavamaven.service.ConsultaUsuarioService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsultaUsuarioControllerTest {

  @InjectMocks
  ConsultaUsuarioController consultaUsuarioController;

  @Mock
  ConsultaUsuarioService consultaUsuarioService;

  @Test
  @DisplayName("Consulta usuarios exitosa")
  void consultaSuccess() {
    UserDescription userDescription = new UserDescription();
    userDescription.setWebsite("web");
    userDescription.setName("nombre");
    userDescription.setUsername("alex");
    List<UserDescription> userDescriptions = Collections.singletonList(userDescription);
    when(consultaUsuarioService.consultaSuccess()).thenReturn(userDescriptions);
    List<UserDescription> list = consultaUsuarioController.consultaSuccess();
    assertTrue(list.size() > 0);
  }

  @Test
  @DisplayName("Consulta usuarios error")
  void consultaError() {
    List<UserDescription> userDescriptions = new ArrayList<>();
    when(consultaUsuarioService.consultaError()).thenReturn(userDescriptions);
    List<UserDescription> list = consultaUsuarioController.consultaError();
    assertEquals(new ArrayList<>(), list);
  }

  @Test
  @DisplayName("Consulta usuarios filtrado")
  void filtrado() {
    UserDescription userDescription = new UserDescription();
    userDescription.setWebsite("web");
    userDescription.setName("nombre");
    userDescription.setUsername("alex");
    List<UserDescription> userDescriptions = Collections.singletonList(userDescription);
    when(consultaUsuarioService.filtrado()).thenReturn(userDescriptions);
    List<UserDescription> list = consultaUsuarioController.filtrado();
    assertTrue(list.size() > 0);
  }
}