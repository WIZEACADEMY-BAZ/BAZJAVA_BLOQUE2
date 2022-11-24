package com.cursojava.proyecto.model;

import com.cursojava.proyecto.utils.herencia.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("pokemonDTO")
public class PokemonDTO {

    private String nombre;

    private TipoDTO tipo1;

    private String ultimoMovimiento;

    private List<Movimiento> movimientos;

    private TipoDTO tipo2;

    private String status;


    public PokemonDTO(){}

    public PokemonDTO(String nombre, String tipo1){
        this.nombre=nombre;
        this.movimientos=new ArrayList<>();
        this.setTipo1(new TipoDTO(tipo1));
    }

    public PokemonDTO(String nombre, String tipo1, String tipo2){
        this.nombre=nombre;
        this.movimientos=new ArrayList<>();
        this.setTipo1(new TipoDTO(tipo1));
        this.setTipo2(new TipoDTO(tipo2));
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public TipoDTO getTipo1() { return tipo1; }

    public void setTipo1(TipoDTO tipo1) {
        this.tipo1 = tipo1;
        agregarMovimientos(tipo1.getNombre());
    }

    public TipoDTO getTipo2() { return this.tipo2; }

    public void setTipo2(TipoDTO tipo2) {
        this.tipo2 = tipo2;
        agregarMovimientos(tipo2.getNombre());
    }

    public List<Movimiento> getMovimientos() {
        return movimientos;
    }

    public void setMovimientos(List<Movimiento> movimientos) {
        this.movimientos = movimientos;
    }

    public String getUltimoMovimiento() {
        return ultimoMovimiento;
    }

    public void setUltimoMovimiento(String ultimoMovimiento) {
        this.ultimoMovimiento = ultimoMovimiento;
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


    public String toStringSimple() {
        return "PokemonDTO{" +
                "nombre='" + nombre + '\'' +
                ", tipo1=" + tipo1.getNombre() +
                '}';
    }
}
