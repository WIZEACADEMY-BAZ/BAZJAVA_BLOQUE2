package com.wizeline.gradle.learningjavagradle.model;

public class NuevoSigDTO extends NuevoDTO{
	  private String nombre;
	  private String texto;
	  
	public NuevoSigDTO() {
		super();
	}

	public NuevoSigDTO(Integer id, String nombre, String texto, String nombreUsuario, Boolean completado) {
		super( id, nombre, nombreUsuario, completado);
		this.nombre = nombre;
		this.texto = texto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}
	
	

	  
}
