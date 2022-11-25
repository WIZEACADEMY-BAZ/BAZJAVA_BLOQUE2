package com.wizeline.maven.learningjava.Learning.service;
import com.wizeline.maven.learningjava.Learning.model.detalle.UserDescription;

import java.util.List;

public interface ConsultaUsuarioService {

    List<UserDescription> consultaSuccess();
    List<UserDescription> consultaError();

    List<UserDescription> filtrado();
}