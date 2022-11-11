package com.wizeline.gradle.learningjavagradle.repository;

import com.wizeline.gradle.learningjavagradle.model.NuevoDTO;

import java.util.List;

public interface NuevoRepository {
List<NuevoDTO>getNuevoUsuario(String usuarioId );
}
