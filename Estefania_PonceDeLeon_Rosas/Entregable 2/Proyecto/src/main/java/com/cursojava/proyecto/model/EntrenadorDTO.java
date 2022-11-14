package com.cursojava.proyecto.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Entrenador")
public class EntrenadorDTO {

    private String nombre;
    private PokemonDTO[] equipo= new PokemonDTO[6];
    private String password;
    private String claveDeSeguridad;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public PokemonDTO[] getEquipo() {
        return equipo;
    }

    public void setEquipo(PokemonDTO[] equipo) {
        this.equipo = equipo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getClaveDeSeguridad() {
        return claveDeSeguridad;
    }

    public void setClaveDeSeguridad(String claveDeSeguridad) {
        this.claveDeSeguridad = claveDeSeguridad;
    }
}
