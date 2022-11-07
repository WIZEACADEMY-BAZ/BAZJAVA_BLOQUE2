package com.wizeline.gradle.practicajava.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.wizeline.gradle.practicajava.model.EstudianteDTO;
import com.wizeline.gradle.practicajava.repository.CursoRepository;
import com.wizeline.gradle.practicajava.utils.exceptions.ExcepcionCurso;

@Service
public class CursoServiceImpl implements CursoService {

	@Autowired
	MongoTemplate mongoTemplate;

	@Autowired
	CursoRepository cursoRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(CursoServiceImpl.class.getName());
	private static String msgProcPeticion = "LearningJava - Inicia procesamiento de petición...";

	@Override
	public List<EstudianteDTO> obtieneEstudiantes() {
		LOGGER.info(msgProcPeticion);
		return mongoTemplate.findAll(EstudianteDTO.class, "estudiantesCollection");
	}

	@Override
	public void guardaEstudiantes(List<EstudianteDTO> estudiantes) {
		LOGGER.info(msgProcPeticion);
		
		estudiantes.stream().forEach(estudiante -> estudiante.setNombre(estudiante.getNombre().toUpperCase()));
		Validaciones validaciones = new Validaciones();
		LocalDateTime fechaActual;
		try {
			for (EstudianteDTO estudiante : estudiantes) {
				if (!validaciones.validaMail(estudiante.getCorreo())) {
					estudiante.setCorreo("No disponible");
				}
				estudiante.setSemestreCadena(validaciones.validaSemestre(estudiante.getSemestre()));
				fechaActual = LocalDateTime.now();
				estudiante.setFechaCreacion(fechaActual);
				LOGGER.info("Se almacena estudiante: {}", estudiante.toString());
				mongoTemplate.save(estudiante);
			}
		}catch (ExcepcionCurso ex){
			LOGGER.info(ex.getMessage());
		}
		

	}

	@Override
	public void borrarEstudiantes() {
		LOGGER.info(msgProcPeticion);
		cursoRepository.deleteAll();
	}

	@Override
	public void actualizarEstudiante(EstudianteDTO estudiante) {
		LOGGER.info(msgProcPeticion);
		Update update = new Update();
		update.set("calificaciones", estudiante.getCalificaciones());
		mongoTemplate.upsert(Query.query(Criteria.where("_id").is(estudiante.getMatricula())), update,
				EstudianteDTO.class);

	}

	public class Validaciones {
		
		public boolean validaMail(String mail) {

			final Pattern patronMail = Pattern.compile("^[^@]+@[^@]+\\.[a-zA-Z]{2,}$");
			boolean valido = false;
			if (patronMail.matcher(mail).matches()) {
				valido = true;
			}
			return valido;

		}
		
		public String validaSemestre(int idSemestre) {
			Map<Integer, String> mapaSemestres = new HashMap<Integer, String>();
			mapaSemestres.put(1, "Primer semestre");
			mapaSemestres.put(2, "Segundo semestre");
			mapaSemestres.put(3, "Tercer semestre");
			mapaSemestres.put(4, "Cuarto semestre");
			mapaSemestres.put(5, "Quinto semestre");
			mapaSemestres.put(6, "Sexto semestre");
			mapaSemestres.put(7, "Septimo semestre");
			mapaSemestres.put(8, "Octavo semestre");
			mapaSemestres.put(9, "Noveno semestre");
			mapaSemestres.put(10, "Decimo semestre");
			mapaSemestres.put(11, "Undecimo semestre");
			mapaSemestres.put(12, "Duodecimo semestre");
			return mapaSemestres.get(idSemestre);
		}
	}

}
