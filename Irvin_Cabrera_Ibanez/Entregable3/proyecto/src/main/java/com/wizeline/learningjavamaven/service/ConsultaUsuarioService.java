package com.wizeline.learningjavamaven.service;

import com.wizeline.learningjavamaven.model.detalle.UserDescription;

import java.util.List;

public interface ConsultaUsuarioService {

  List<UserDescription> consultaSuccess();
  List<UserDescription> consultaError();

  List<UserDescription> filtrado();
}
