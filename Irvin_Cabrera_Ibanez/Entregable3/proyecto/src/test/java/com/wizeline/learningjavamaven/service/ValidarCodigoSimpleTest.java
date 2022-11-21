package com.wizeline.learningjavamaven.service;

import com.wizeline.learningjavamaven.model.detalle.UserDescription;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class ValidarCodigoSimpleTest {

  @InjectMocks
  ValidarCodigoSimple validarCodigoSimple;

  @Test
  @DisplayName("Validacion codigo")
  void validarCodigo() {
    UserDescription.Address address = new UserDescription.Address();
    address.setZipcode("23505");
    boolean valido = validarCodigoSimple.validarCodigo(address);
    assertTrue(valido);
  }

  @Test
  @DisplayName("Validacion codigo error")
  void validarCodigoFalso() {
    UserDescription.Address address = new UserDescription.Address();
    address.setZipcode("10000");
    boolean valido = validarCodigoSimple.validarCodigo(address);
    assertFalse(valido);
  }
}