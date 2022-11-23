package com.wizeline.learningjavamaven.model;

public class UserDateDTO {

  private String user;
  private String anio;
  private String fechaNacimiento;

  public UserDateDTO() {
  }

  public UserDateDTO(String user, String anio, String fechaNacimiento) {
    this.user = user;
    this.anio = anio;
    this.fechaNacimiento = fechaNacimiento;
  }

  public String getUser() {
    return user;
  }

  public void setUser(String user) {
    this.user = user;
  }

  public String getAnio() {
    return anio;
  }

  public void setAnio(String anio) {
    this.anio = anio;
  }

  public String getFechaNacimiento() {
    return fechaNacimiento;
  }

  public void setFechaNacimiento(String fechaNacimiento) {
    this.fechaNacimiento = fechaNacimiento;
  }
}
