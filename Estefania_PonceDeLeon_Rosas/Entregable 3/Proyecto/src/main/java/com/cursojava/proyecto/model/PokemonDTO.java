package com.cursojava.proyecto.model;

import com.cursojava.proyecto.utils.herencia.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

@Document("pokemonDTO")
public class PokemonDTO {

    private String nombre;

    private String sonido;

    private TipoDTO tipo1;

    private Movimiento[] movimientos = new Movimiento[2];

    private TipoDTO tipo2;

    private LocalDateTime lastTraning;

    private String date;

    private String status;

    private Optional<PokemonDTO> evolucion;

    public PokemonDTO(){}

    public PokemonDTO(String nombre, String tipo1){
        this.nombre=nombre;
        this.setTipo1(new TipoDTO(tipo1));
    }

    public PokemonDTO(String nombre, String tipo1, String tipo2){
        this.nombre=nombre;
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

    public void setTipo1(TipoDTO tipo1) { this.tipo1 = tipo1; agregarMovimientos(0,tipo1.getNombre());}

    public TipoDTO getTipo2() { return tipo2; }

    public void setTipo2(TipoDTO tipo2) { this.tipo2 = tipo2; if(tipo2.getNombre()!=null) agregarMovimientos(1,tipo2.getNombre());}

    public Movimiento[] getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(Movimiento[] movimientos) {
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

    private void agregarMovimientos(int i, String tipo){
        switch (tipo){
            case "Planta":
                this.movimientos[i]= new Planta();
                break;
            case "Agua":
                this.movimientos[i]= new Agua();
                break;
            case "Fuego":
                this.movimientos[i]= new Fuego();
                break;
            case "Normal":
                this.movimientos[i]= new Normal();
                break;
            case "Volador":
                this.movimientos[i] = new Volador();
                break;
            case "Veneno":
                this.movimientos[i] =new Veneno();
                break;
            case "Tierra":
                this.movimientos[i] =new Tierra();
                break;
            case "Electrico":
                this.movimientos[i] =new Electrico();
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
