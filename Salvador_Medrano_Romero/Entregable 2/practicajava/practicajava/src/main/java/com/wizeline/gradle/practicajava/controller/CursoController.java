package com.wizeline.gradle.practicajava.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import com.wizeline.gradle.practicajava.config.JwtTokenConfig;
import com.wizeline.gradle.practicajava.model.EstudianteDTO;
import com.wizeline.gradle.practicajava.model.UserDTO;
import com.wizeline.gradle.practicajava.service.CursoService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/apiCurso")
public class CursoController {

	@Autowired
	CursoService cursoService;

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	JwtTokenConfig jwtTokenCongif;

	@Autowired
	RestTemplate restTemplate;

	@PostMapping("/guardarEstudiantes")
	public ResponseEntity<String> guardarEstudiantes(@RequestBody List<EstudianteDTO> estudiantes) {
		cursoService.guardaEstudiantes(estudiantes);
		return new ResponseEntity<>("Se guardaron todos los estudiantes", HttpStatus.OK);
	}

	@GetMapping(value = "/obtenerEstudiantes", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EstudianteDTO>> obtenerEstudiantes() {
		List<EstudianteDTO> estudiantes = cursoService.obtieneEstudiantes();
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
		return new ResponseEntity<>(estudiantes, responseHeaders, HttpStatus.OK);
	}

	@GetMapping(value = "/obtenerCalificaciones", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<int[]> obtenerCalificaciones(@RequestParam String matricula) {
		int[] calificaciones = cursoService.obtenerCalificaciones(matricula);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
		return new ResponseEntity<>(calificaciones, responseHeaders, HttpStatus.OK);
	}

	@DeleteMapping("/borrarEstudiantes")
	public ResponseEntity<String> borrarEstudiantes() {
		cursoService.borrarEstudiantes();
		return new ResponseEntity<>("Estudiantes eliminados", HttpStatus.OK);
	}

	@PutMapping("/actualizarEstudiante")
	public ResponseEntity<String> actualizarEstudiante(@RequestBody EstudianteDTO estudiante) {
		cursoService.actualizarEstudiante(estudiante);
		return new ResponseEntity<>("Se actualizo al estudiante", HttpStatus.OK);
	}

	@GetMapping(value = "/consumeRestTemplate", produces = MediaType.APPLICATION_JSON_VALUE)
	public String restTemplate() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return restTemplate.exchange("https://reqres.in/api/users?page=2", HttpMethod.GET, entity, String.class)
				.getBody();
	}

	@PostMapping("/autenticacion")
	public ResponseEntity<?> obtenerToken(@RequestBody UserDTO userDTO) {
		UserDetails userDetails;
		try {
			userDetails = userDetailsService.loadUserByUsername(userDTO.getUser());
		} catch (UsernameNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Usuario no encontrado");
		}
		Claims claims = Jwts.claims().setSubject(userDTO.getUser());
		claims.put("username", userDTO.getUser());
		String authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect(Collectors.joining(","));
		claims.put("authorities", authorities);
		claims.put("date", new Date());

		String token = jwtTokenCongif.generateToken(userDTO, claims);
		return ResponseEntity.ok(token);
	}
}
