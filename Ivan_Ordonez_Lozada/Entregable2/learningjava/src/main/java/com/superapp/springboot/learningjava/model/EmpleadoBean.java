package com.superapp.springboot.learningjava.model;

import com.superapp.springboot.learningjava.enums.Puesto;

public class EmpleadoBean {

	private long empleado_id;
	private String nombre;
	private Puesto puesto;
	protected boolean estatus;
	
	public EmpleadoBean() {
		super();
	}

	//Sobrecarga - constructor
	public EmpleadoBean(long empleado_id, String nombre, Puesto puesto) {
		super();
		this.empleado_id = empleado_id;
		this.nombre = nombre;
		this.puesto = puesto;
		this.estatus = false;
	}

	public long getEmpleado_id() {
		return empleado_id;
	}

	public void setEmpleado_id(long empleado_id) {
		this.empleado_id = empleado_id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Puesto getPuesto() {
		return puesto;
	}

	public void setPuesto(Puesto puesto) {
		this.puesto = puesto;
	}
	
	//Sobrecarga - método
	public void cambiarEstatus(boolean estatus) {
		if(estatus == true)
			this.estatus = false;
		else
			this.estatus = true;
	}
}
