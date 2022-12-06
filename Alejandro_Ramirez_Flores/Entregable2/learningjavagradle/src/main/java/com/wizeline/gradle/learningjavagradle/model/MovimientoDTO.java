package com.wizeline.gradle.learningjavagradle.model;

public class MovimientoDTO {
	private String tipo;
	private double cantidad;
	
	
	
	public MovimientoDTO() {
		super();
	}

	public MovimientoDTO(String tipo, double cantidad) {
		super();
		this.tipo = tipo;
		this.cantidad = cantidad;
	}
	
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public double getCantidad() {
		return cantidad;
	}
	public void setCantidad(double cantidad) {
		this.cantidad = cantidad;
	}

	
}