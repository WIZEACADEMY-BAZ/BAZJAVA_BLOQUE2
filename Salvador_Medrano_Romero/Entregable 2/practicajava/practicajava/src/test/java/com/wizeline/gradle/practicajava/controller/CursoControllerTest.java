package com.wizeline.gradle.practicajava.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wizeline.gradle.practicajava.enums.Turno;
import com.wizeline.gradle.practicajava.model.EstudianteDTO;
import com.wizeline.gradle.practicajava.service.CursoServiceImpl;

import io.github.bucket4j.Bucket;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
class CursoControllerTest {

	@MockBean
	private CursoServiceImpl cursoService;

	@Autowired
	private CursoController cursoController;

	@MockBean
	private KafkaTemplate<Object, Object> kafkaTemplate;

	@MockBean
	private Bucket bucket;

	@Mock
	private RestTemplate restTemplate;

	@Test
	void sendUserAccountTest() throws Exception {
		List<EstudianteDTO> estudiantes = new ArrayList<>();
		EstudianteDTO estudiante1 = new EstudianteDTO();
		estudiante1.setApellidoMat("ApellidoMat");
		estudiante1.setApellidoPat("ApellidoPat");
		int[] calificaciones = { 10, 9, 8 };
		estudiante1.setCalificaciones(calificaciones);
		estudiante1.setCorreo("correo@mail.com");
		estudiante1.setFechaCreacion(LocalDateTime.now());
		estudiante1.setMatricula("matricula1");
		estudiante1.setNombre("Juan");
		estudiante1.setSemestre(6);
		estudiante1.setTurno(Turno.MATUTINO);
		estudiante1.setUniversidad("UNAM");
		estudiantes.add(estudiante1);
		when(cursoService.obtieneEstudiantes()).thenReturn(estudiantes);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/apiCurso/send/0");
		;
		MockMvcBuilders.standaloneSetup(cursoController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk());
	}

	@Test
	void guardarEstudiantesTest() throws Exception {
		List<EstudianteDTO> estudiantes = new ArrayList<>();
		EstudianteDTO estudiante1 = new EstudianteDTO();
		estudiante1.setApellidoMat("ApellidoMat");
		estudiante1.setApellidoPat("ApellidoPat");
		int[] calificaciones = { 10, 9, 8 };
		estudiante1.setCalificaciones(calificaciones);
		estudiante1.setCorreo("correo@mail.com");
		estudiante1.setFechaCreacion(LocalDateTime.now());
		estudiante1.setMatricula("matricula1");
		estudiante1.setNombre("Juan");
		estudiante1.setSemestre(6);
		estudiante1.setTurno(Turno.MATUTINO);
		estudiante1.setUniversidad("UNAM");
		estudiantes.add(estudiante1);

		doNothing().when(cursoService).guardaEstudiantes(estudiantes);
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/apiCurso/guardarEstudiantes")
				.contentType(MediaType.APPLICATION_JSON);
		ObjectMapper objectMapper = new ObjectMapper();
		MockHttpServletRequestBuilder requestBuilder = contentTypeResult
				.content(objectMapper.writeValueAsString(new ArrayList<>()));
		MockMvcBuilders.standaloneSetup(cursoController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Se guardaron todos los estudiantes"));
	}

	@Test
	void obtenerEstudiantesTest() throws Exception {
		when(cursoService.obtieneEstudiantes()).thenReturn(new ArrayList<>());
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/apiCurso/obtenerEstudiantes");
		MockMvcBuilders.standaloneSetup(cursoController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json; charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	@Test
	void obtenerCalificacionesTest() throws Exception {
		int[] calificaciones = {};
		when(cursoService.obtenerCalificaciones((String) any())).thenReturn(calificaciones);
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/apiCurso/obtenerCalificaciones")
				.param("matricula", "values");
		MockMvcBuilders.standaloneSetup(cursoController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json; charset=UTF-8"))
				.andExpect(MockMvcResultMatchers.content().string("[]"));
	}

	@Test
	void borrarEstudiantesTest() throws Exception {
		doNothing().when(cursoService).borrarEstudiantes();
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/apiCurso/borrarEstudiantes");
		MockMvcBuilders.standaloneSetup(cursoController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
				.andExpect(MockMvcResultMatchers.content().string("Estudiantes eliminados"));
	}

	@Test
	void actualizarEstudianteTest() throws Exception {

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

		Map<String, Object> body = new HashMap<>();
		body.put("apellidoMat", "ApellidoMat");
		body.put("apellidoPat", "ApellidoPat");
		int[] arr = { 10, 9, 8 };
		body.put("calificaciones", arr);
		body.put("nombre", "Persona");
		body.put("semestre", "6");

		ObjectMapper objectMapper = new ObjectMapper();
		doNothing().when(cursoService).actualizarEstudiante(estudiante);
		MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/apiCurso/actualizarEstudiante")
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(body));

		MockMvcBuilders.standaloneSetup(cursoController).build().perform(contentTypeResult)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Se actualizo al estudiante"));

	}

	@Test
	void obtenerEstudiantesTooManyRequestTest() throws Exception {

		List<EstudianteDTO> estudiantes = new ArrayList<>();
		EstudianteDTO estudiante1 = new EstudianteDTO();
		estudiante1.setApellidoMat("ApellidoMat");
		estudiante1.setApellidoPat("ApellidoPat");
		int[] calificaciones = { 10, 9, 8 };
		estudiante1.setCalificaciones(calificaciones);
		estudiante1.setCorreo("correo@mail.com");
		estudiante1.setFechaCreacion(LocalDateTime.now());
		estudiante1.setMatricula("matricula1");
		estudiante1.setNombre("Juan");
		estudiante1.setSemestre(6);
		estudiante1.setTurno(Turno.MATUTINO);
		estudiante1.setUniversidad("UNAM");
		estudiantes.add(estudiante1);

		when(bucket.tryConsume(1)).thenReturn(false);
		when(cursoService.obtieneEstudiantes()).thenReturn(estudiantes);

		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/apiCurso/obtenerEstudiantes");

		MockMvc nombre = MockMvcBuilders.standaloneSetup(cursoController).build();
		nombre.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
		nombre.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
		nombre.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
		nombre.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isOk());
		nombre.perform(requestBuilder).andExpect(MockMvcResultMatchers.status().isTooManyRequests());
	}

	@Test
	void restTemplateTest() throws Exception {
		MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/apiCurso/consumeRestTemplate");
		MockMvcBuilders.standaloneSetup(cursoController).build().perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType("application/json"))
				.andExpect(MockMvcResultMatchers.content().string(
						"{\"page\":2,\"per_page\":6,\"total\":12,\"total_pages\":2,\"data\":[{\"id\":7,\"email\":\"michael.lawson@reqres.in\",\"first_name\":\"Michael\",\"last_name\":\"Lawson\",\"avatar\":\"https://reqres.in/img/faces/7-image.jpg\"},{\"id\":8,\"email\":\"lindsay.ferguson@reqres.in\",\"first_name\":\"Lindsay\",\"last_name\":\"Ferguson\",\"avatar\":\"https://reqres.in/img/faces/8-image.jpg\"},{\"id\":9,\"email\":\"tobias.funke@reqres.in\",\"first_name\":\"Tobias\",\"last_name\":\"Funke\",\"avatar\":\"https://reqres.in/img/faces/9-image.jpg\"},{\"id\":10,\"email\":\"byron.fields@reqres.in\",\"first_name\":\"Byron\",\"last_name\":\"Fields\",\"avatar\":\"https://reqres.in/img/faces/10-image.jpg\"},{\"id\":11,\"email\":\"george.edwards@reqres.in\",\"first_name\":\"George\",\"last_name\":\"Edwards\",\"avatar\":\"https://reqres.in/img/faces/11-image.jpg\"},{\"id\":12,\"email\":\"rachel.howell@reqres.in\",\"first_name\":\"Rachel\",\"last_name\":\"Howell\",\"avatar\":\"https://reqres.in/img/faces/12-image.jpg\"}],\"support\":{\"url\":\"https://reqres.in/#support-heading\",\"text\":\"To keep ReqRes free, contributions towards server costs are appreciated!\"}}"));

	}

}
