package com.wizeline.maven.learningjavamaven.model;

public class DataModel {

    private String name;
    private String ciudad;
    private int edad;

    //Constructor sobrecargado

    public DataModel(String name, String ciudad, int edad) {
        this.name = name;
        this.ciudad = ciudad;
        this.edad = edad;
    }

    public DataModel(String name){
        this.name = name;
        this.ciudad ="Villa Victoria";
    }


    public DataModel(String name, int edad){
        this.edad = edad;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void cambiar (String name){
        this.name = name;
    }
    public void cambiar(int edad){
        this.edad = edad;
    }

    //Aqui se emplea metodo cambiar sobrecargado
    public void cambiar(String name, int edad){
        this.edad = edad;
        this.name = name;
    }
}
