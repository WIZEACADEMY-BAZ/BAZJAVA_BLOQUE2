package com.wizeline.learningjavamaven.service;

import com.wizeline.learningjavamaven.model.detalle.UserDescription;

public class ValidarCodigoSimple implements ValidarCodigo<UserDescription.Address> {

  @Override
  public boolean validarCodigo(UserDescription.Address domicilio) {
    String[] codigo = domicilio.getZipcode().split("-");
    return listaCodigos.contains(codigo[0]);
  }
}
