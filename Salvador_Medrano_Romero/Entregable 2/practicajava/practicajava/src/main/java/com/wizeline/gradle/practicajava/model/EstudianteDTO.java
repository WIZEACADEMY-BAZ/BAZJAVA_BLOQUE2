package com.wizeline.gradle.practicajava.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.wizeline.gradle.practicajava.enums.Turno;

@Document("estudiantesCollection")
public class EstudianteDTO extends PersonaDTO {

	@Id
	private String matricula;
	private Turno turno;
	private String universidad;
	private int[] calificaciones;
	private LocalDateTime fechaCreacion;
	private String correo;
	private int semestre;
	private String semestreCadena;

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public int[] getCalificaciones() {
		return calificaciones;
	}

	public void setCalificaciones(int[] calificaciones) {
		this.calificaciones = calificaciones;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public Turno getTurno() {
		return turno;
	}

	public void setTurno(Turno turno) {
		this.turno = turno;
	}

	public String getUniversidad() {
		return universidad;
	}

	public void setUniversidad(String universidad) {
		this.universidad = universidad;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public int getSemestre() {
		return semestre;
	}

	public void setSemestre(int semestre) {
		this.semestre = semestre;
	}

	public String getSemestreCadena() {
		return semestreCadena;
	}

	public void setSemestreCadena(String semestreCadena) {
		this.semestreCadena = semestreCadena;
	}

	@Override
	public String toString() {
		return this.getMatricula() + "\n" + this.getNombre() + "\n" + this.getApellidoPat() + "\n"
				+ this.getApellidoMat();

	}

}
