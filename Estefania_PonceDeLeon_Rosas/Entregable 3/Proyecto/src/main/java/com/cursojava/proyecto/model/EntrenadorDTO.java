package com.cursojava.proyecto.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Entrenador")
public class EntrenadorDTO {

    private String nombre;
    private PokemonDTO[] equipo= new PokemonDTO[6];
    private String password;
    private String claveDeSeguridad;
    private String rango;

    public EntrenadorDTO(){}

    public EntrenadorDTO(String nombre){
        this.nombre=nombre;
    }
    public EntrenadorDTO(String nombre, String password){
        this.nombre=nombre;
        this.password=password;
    }
    public EntrenadorDTO(String nombre, String password, String claveDeSeguridad){
        this.nombre=nombre;
        this.password=password;
        this.claveDeSeguridad=claveDeSeguridad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre){this.nombre=nombre;}

    public void setEquipo(PokemonDTO[] equipo) {
        this.equipo = equipo;
    }

    public String getPassword() {
        return password;
    }

    public String getClaveDeSeguridad() {
        return claveDeSeguridad;
    }

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }

}
