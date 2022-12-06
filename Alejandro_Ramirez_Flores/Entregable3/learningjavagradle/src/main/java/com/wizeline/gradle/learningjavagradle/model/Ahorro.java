package com.wizeline.gradle.learningjavagradle.model;

import org.springframework.stereotype.Repository;

@Repository
public class Ahorro implements Account{
	private int efectivo;
	private int accountNumber;
	
	public int getAccountNumber() {return accountNumber;}
	//@Override
	public void setAccountNumber(int accountNumber) {this.accountNumber = accountNumber;}
	
	public int getEfectivo() {return efectivo;}
	
	public void setEfectivo(int efectivo) {this.efectivo = efectivo;}
	
	public void tieneFondos() {
		if(efectivo > 0) {
			System.out.println("Tienes efectivo" + efectivo);
		}else {
			System.out.println("Ya No Tienes efectivo" + efectivo);
		}
	}
	
	public void cuentaValida() {
		if(efectivo >= 4000 && efectivo < 100000) {
			System.out.println("Tienes Cuenta Ahorro");
			}
	}
}
