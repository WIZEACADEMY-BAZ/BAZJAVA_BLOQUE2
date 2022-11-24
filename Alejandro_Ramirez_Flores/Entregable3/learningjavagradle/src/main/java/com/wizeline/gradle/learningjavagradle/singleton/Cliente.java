package com.wizeline.gradle.learningjavagradle.singleton;

public class Cliente {

	private  String nombre;
	private  String apellido;
	private double saldo;
	
	private static Cliente instance = new Cliente();
	
	//private Cliente() {}
	private Cliente() {
		super();
		this.nombre = nombre;
		this.apellido = apellido;
		this.saldo = saldo;
	}

	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}


	public static Cliente getInstance() {
		return instance;
	}
}
