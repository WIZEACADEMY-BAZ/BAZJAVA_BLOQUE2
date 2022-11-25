package com.wizeline.gradle.practicajava.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import com.wizeline.gradle.practicajava.model.EstudianteDTO;

public class CursoItemProcessor implements ItemProcessor<EstudianteDTO, String> {

	private static final Logger LOGGER = LoggerFactory.getLogger(CursoItemProcessor.class);

	@Override
	public String process(EstudianteDTO studianteDTO) throws Exception {
		String accounts = "Matricula: " + studianteDTO.getMatricula() + " Nombre: " + studianteDTO.getNombre()
				+ " Turno: " + studianteDTO.getTurno() + " Universidad: " + studianteDTO.getUniversidad() + " Correo: "
				+ studianteDTO.getCorreo();
		LOGGER.info("converting '{}' into '{}'" + studianteDTO + accounts);
		return accounts;
	}
}
