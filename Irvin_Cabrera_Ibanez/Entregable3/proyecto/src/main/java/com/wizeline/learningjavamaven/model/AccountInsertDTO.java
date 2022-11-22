package com.wizeline.learningjavamaven.model;

import com.wizeline.learningjavamaven.enums.Country;

import java.time.LocalDateTime;

public class AccountInsertDTO {

  private String usuario;
  private Country country;
  private LocalDateTime dateTime;
  private boolean activo;

  public AccountInsertDTO(String usuario, Country country, LocalDateTime dateTime, boolean activo) {
    this.usuario = usuario;
    this.country = country;
    this.dateTime = dateTime;
    this.activo = activo;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public boolean isActivo() {
    return activo;
  }

  public void setActivo(boolean activo) {
    this.activo = activo;
  }
}
