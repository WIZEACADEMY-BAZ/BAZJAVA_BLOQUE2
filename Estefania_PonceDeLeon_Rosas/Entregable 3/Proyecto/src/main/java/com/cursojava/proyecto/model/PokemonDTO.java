package com.cursojava.proyecto.model;
import com.cursojava.proyecto.utils.herencia.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Document("pokemonDTO")
public class PokemonDTO {

    private String nombre;

    private String sonido;

    private TipoDTO tipo1;

    private List<Movimiento> movimientos;

    private TipoDTO tipo2;

    private LocalDateTime lastTraning;

    private String date;

    private String status;

    private Optional<PokemonDTO> evolucion;

    public PokemonDTO(){}

    public PokemonDTO(String nombre, String tipo1){
        this.nombre=nombre;
        this.movimientos=new ArrayList<>();
        this.setTipo1(new TipoDTO(tipo1));
        this.tipo2=new TipoDTO();
        agregarMovimientos(tipo1);

    }

    public PokemonDTO(String nombre, String tipo1, String tipo2){
        this.nombre=nombre;
        this.movimientos=new ArrayList<>();
        this.setTipo1(new TipoDTO(tipo1));
        if(tipo2!=null){
            this.setTipo2(new TipoDTO(tipo2));
        }
    }

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

    public TipoDTO getTipo1() { return tipo1; }

    public void setTipo1(TipoDTO tipo1) { this.tipo1 = tipo1;}

    public TipoDTO getTipo2() { return this.tipo2; }

    public void setTipo2(TipoDTO tipo2) { this.tipo2 = tipo2;
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public LocalDateTime getLastTraning() {
        return lastTraning;
    }

    public void setLastTraning(LocalDateTime lastTraning) {
        this.lastTraning = lastTraning;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private void agregarMovimientos(String tipo){
        switch (tipo){
            case "Planta":
                this.movimientos.add(new Planta());
                break;
            case "Agua":
                this.movimientos.add(new Agua());
                break;
            case "Fuego":
                this.movimientos.add(new Fuego());
                break;
            case "Normal":
                this.movimientos.add(new Normal());
                break;
            case "Volador":
                this.movimientos.add(new Volador());
                break;
            case "Veneno":
                this.movimientos.add(new Veneno());
                break;
            case "Tierra":
                this.movimientos.add(new Tierra());
                break;
            case "Electrico":
                this.movimientos.add(new Electrico());
                break;
        }
    }

    public Optional<PokemonDTO> getEvolucion() {
        return evolucion;
    }

    public void setEvolucion(Optional<PokemonDTO> evolucion) {
        this.evolucion = evolucion;
    }

    @Override
    public String toString() {
        return "PokemonDTO{" +
                "nombre='" + nombre + '\'' +
                ", sonido='" + sonido + '\'' +
                ", tipo1=" + tipo1.getNombre() +
                ", tipo2=" + tipo2.getNombre() +
                ", lastTraning=" + lastTraning +
                ", status='" + status + '\'' +
                '}';
    }

    public String toStringSimple() {
        return "PokemonDTO{" +
                "nombre='" + nombre + '\'' +
                ", sonido='" + sonido + '\'' +
                ", tipo1=" + tipo1.getNombre() +
                '}';
    }
}
