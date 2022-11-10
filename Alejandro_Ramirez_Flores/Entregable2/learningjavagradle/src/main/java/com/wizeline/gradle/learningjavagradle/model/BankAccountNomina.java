package com.wizeline.gradle.learningjavagradle.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.data.mongodb.core.mapping.Document;

import com.wizeline.gradle.learningjavagradle.utils.Utils;

@Document("BankAccountNomina")
public class BankAccountNomina extends BankAccountDTO {

	private static final Logger LOGGER = Logger.getLogger(BankAccountDTO.class.getName());

	private String nombreUser;
	private String apellidosUser;
	private String rfc;
	private Map<String, String> contactos = new HashMap<String,String>();
	private List<MovimientoDTO> movimientos;
	
	public BankAccountNomina() {
		super();
	}

	public BankAccountNomina(String nombreUser, String apellidosUser, String rfc) {
		super();
		/*
		 * metodo isRFCvalid utiliza una expresion regular para validar el RFC
		 */
		if(Utils.isRFCvalid(rfc)) {		
		this.nombreUser = nombreUser;
		this.apellidosUser = apellidosUser;
		this.rfc = rfc;
		}else {
		     LOGGER.info("El RFC no es valido");
		}
	}

	public BankAccountNomina(String nombreUser, String apellidosUser) {
		super();
		this.nombreUser = nombreUser;
		this.apellidosUser = apellidosUser;
	}

	/*
	 * Sobrecarga de metodos
	 */
	public void deposito(double cantidad) {
		double balance = super.getAccountBalance();
		balance += cantidad;
		super.setAccountBalance(balance);
		LOGGER.info("Cantidad depositada $" + cantidad);
	}
	
	public void deposito(double cantidad, int tipo) {
		MovimientoDTO movimiento;
		double balance = super.getAccountBalance();
		balance += cantidad;
		super.setAccountBalance(balance);
		if(tipo==1) {
			LOGGER.info("Recibiste deposito de tu nomina por la cantidad de $" + cantidad);
			movimiento= new MovimientoDTO("DEPOSITO NOMINA",cantidad);
			/*
			 * uso de lista
			 */
			movimientos.add(movimiento);
		}else if(tipo==2) {
			LOGGER.info("Recibiste deposito por la cantidad de $" + cantidad);
			movimiento= new MovimientoDTO("DEPOSITO TERCERO",cantidad);		
			movimientos.add(movimiento);
		}else {
			LOGGER.info("Cantidad depositada $" + cantidad);
			movimiento= new MovimientoDTO("DEPOSITO A CUENTA",cantidad);		
			movimientos.add(movimiento);
		}
	}
	
	/*
	 * Uso de Map
	 */
	public void AgregarContacto(String nombre,String accountNumber) {
		contactos.put(nombre, accountNumber);
	}

	public String getNombreUser() {
		return nombreUser;
	}

	public void setNombreUser(String nombreUser) {
		this.nombreUser = nombreUser;
	}

	public String getApellidosUser() {
		return apellidosUser;
	}

	public void setApellidosUser(String apellidosUser) {
		this.apellidosUser = apellidosUser;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	
	 // Uso de Clase interna 
	
	public void muestraMensaje() {
		BankAccountNomina.claseInterna interna = new BankAccountNomina.claseInterna();
		interna.mensaje();
	}

	class claseInterna{
		private final Logger LOGGER = Logger.getLogger(BankAccountDTO.class.getName());
		public void mensaje() {
			LOGGER.info("Este mensaje se envia unicamente por la clase BankAccountNomina");
		}
	}

}
