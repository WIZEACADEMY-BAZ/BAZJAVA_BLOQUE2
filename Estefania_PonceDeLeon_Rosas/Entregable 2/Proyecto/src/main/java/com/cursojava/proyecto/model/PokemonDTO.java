package com.cursojava.proyecto.model;

import com.cursojava.proyecto.Enum.Tipo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class PokemonDTO {

    private String nombre;

    private String sonido;

    private Tipo tipo1;

    private Optional<Tipo> tipo2;

    private Optional<PokemonDTO> evolucion;

    private Optional<LocalDateTime> lastTraning;

    private Optional<String> date;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSonido() {
        return sonido;
    }

    public void setSonido(String sonido) {
        this.sonido = sonido;
    }

    public Tipo getTipo1() { return tipo1; }

    public void setTipo1(Tipo tipo1) {
        this.tipo1 = tipo1;
    }

    public Optional<Tipo> getTipo2() { return tipo2; }

    public void setTipo2(Optional<Tipo> tipo2) { this.tipo2 = tipo2; }

    public Optional<PokemonDTO> getEvolucion() {
        return evolucion;
    }

    public void setEvolucion(Optional<PokemonDTO> evolucion) {
        this.evolucion = evolucion;
    }

    public Optional<LocalDateTime> getLastTraning() {
        return lastTraning;
    }

    public void setLastTraning(Optional<LocalDateTime> lastTraning) {
        this.lastTraning = lastTraning;
    }

    public Optional<String> getDate() { return date; }

    public void setDate(Optional<String> date) { this.date = date; }

    @Override
    public String toString() {
        return "PokemonDTO{" +
                "nombre='" + nombre + '\'' +
                ", sonido='" + sonido + '\'' +
                ", tipo1=" + tipo1 +
                '}';
    }
}
