package com.wizeline.gradle.learningjavagradle.model;

public class NuevoDTO {
	//Se implementa el encapsulamiento de una clase
	 private Integer id;
	  private Integer usuarioId;
	  private String titulo;
	  private Boolean completado;
	  private String nombreUsuario;

	  public NuevoDTO(){
	  }

	  public NuevoDTO(Integer id
	      , Integer usuarioId
	      , String titulo
	      , Boolean completado){
	    this.id = id;
	    this.usuarioId = usuarioId;
	    this.titulo = titulo;
	    this.completado = completado;
	  }

	  // Sobrecarga de un constructor de clase
	  public NuevoDTO(Integer id
	      , String nombreUsuario
	      , String titulo
	      , Boolean completado){
	    this.id = id;
	    this.nombreUsuario = nombreUsuario;
	    this.titulo = titulo;
	    this.completado = completado;
	  }
	  
	  //Se accede a la clase por medio de Getters y Setters

	  public Integer getId() {
	    return id;
	  }

	  public void setId(Integer id) {
	    this.id = id;
	  }

	  public Integer getUsuarioId() {
	    return usuarioId;
	  }

	  public void setUsuarioId(Integer usuarioId) {
	    this.usuarioId = usuarioId;
	  }

	  public String getTitulo() {
	    return titulo;
	  }

	  public void setTitulo(String titulo) {
	    this.titulo = titulo;
	  }

	  public Boolean getCompletado() {
	    return completado;
	  }

	  public void setCompletado(Boolean completado) {
	    this.completado = completado;
	  }

	  public String getNombreUsuario() {
	    return nombreUsuario;
	  }

	  public void setNombreUsuario(String nombreUsuario) {
	    this.nombreUsuario = nombreUsuario;
	  }
	}

