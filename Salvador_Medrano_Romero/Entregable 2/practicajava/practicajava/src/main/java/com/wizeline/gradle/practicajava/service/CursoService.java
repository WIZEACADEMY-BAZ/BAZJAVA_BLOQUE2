package com.wizeline.gradle.practicajava.service;

import java.util.List;

import com.wizeline.gradle.practicajava.model.EstudianteDTO;

public interface CursoService {
	List<EstudianteDTO> obtieneEstudiantes();
	void guardaEstudiantes(List<EstudianteDTO> estudiantes);
	void borrarEstudiantes();
	void actualizarEstudiante(EstudianteDTO estudiante);
}
