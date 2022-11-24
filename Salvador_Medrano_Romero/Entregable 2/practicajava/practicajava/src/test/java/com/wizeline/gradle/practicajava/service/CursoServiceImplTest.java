package com.wizeline.gradle.practicajava.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.wizeline.gradle.practicajava.enums.Turno;
import com.wizeline.gradle.practicajava.model.EstudianteDTO;
import com.wizeline.gradle.practicajava.repository.CursoRepository;

@ContextConfiguration(classes = { CursoServiceImpl.class })
@ExtendWith(SpringExtension.class)
class CursoServiceImplTest {
	
	@MockBean
	private MongoTemplate mongoTemplate;

	@Autowired
	private CursoServiceImpl cursoServiceImpl;

	@MockBean
	private CursoRepository cursoRepository;
	
	/**
	 * Metodo Test: {@link CursoServiceImpl#obtieneEstudiantes()}
	 */
	@Test
	void obtieneEstudiantesTest() {
		
		List<EstudianteDTO> estudiantes = new ArrayList<>();
		EstudianteDTO estudiante = new EstudianteDTO();
		
		estudiante.setApellidoMat("ApellidoMat");
		estudiante.setApellidoPat("ApellidoPat");
		int[] calificaciones = { 10, 9, 8 };
		estudiante.setCalificaciones(calificaciones);
		estudiante.setFechaCreacion(LocalDateTime.now());
		estudiante.setMatricula("matricula1");
		estudiante.setNombre("Juan");
		estudiante.setSemestre(6);
		estudiante.setTurno(Turno.MATUTINO);
		estudiante.setUniversidad("UNAM");
		estudiantes.add(estudiante);

		when(mongoTemplate.findAll((Class<EstudianteDTO>) any())).thenReturn(new ArrayList<>());
		assertEquals(1, cursoServiceImpl.obtieneEstudiantes().size());
		verify(mongoTemplate, atLeast(1)).save((EstudianteDTO) any());
		verify(mongoTemplate).findAll((Class<EstudianteDTO>) any());
	}

}
