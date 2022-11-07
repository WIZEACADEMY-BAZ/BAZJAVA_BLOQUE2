package com.cursojava.proyecto.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document("TipoDTO")
public class TipoDTO {
    private String nombre;
    private List<String> movimientos;

    public TipoDTO(){};
    public TipoDTO(String nombre){
        this.nombre=nombre;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "TipoDTO{" +
                "nombre='" + nombre + '\'' +
                ", movimientos=" + movimientos +
                '}';
    }
}
