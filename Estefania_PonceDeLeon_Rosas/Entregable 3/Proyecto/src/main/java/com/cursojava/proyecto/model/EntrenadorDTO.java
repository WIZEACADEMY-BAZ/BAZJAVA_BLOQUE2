package com.cursojava.proyecto.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("Entrenador")
public class EntrenadorDTO {

    private String nombre;
    private PokemonDTO[] equipo= new PokemonDTO[6];
    private String password;
    private String claveDeSeguridad;
    private String rango;

    private String clasificacion;


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

    public EntrenadorDTO(String nombre, String password, String claveDeSeguridad, String rango){
        this.nombre=nombre;
        this.password=password;
        this.claveDeSeguridad=claveDeSeguridad;
        this.rango=rango;
    }

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

    public String getRango() {
        return rango;
    }

    public void setRango(String rango) {
        this.rango = rango;
    }


    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getEquipoString(){
        String equipo="";
        for(int i=0; i<this.equipo.length; i++){
            if(this.equipo[i]!=null)
                equipo=equipo+", "+this.equipo[0].toString();
        }
        return equipo;
    }


}
