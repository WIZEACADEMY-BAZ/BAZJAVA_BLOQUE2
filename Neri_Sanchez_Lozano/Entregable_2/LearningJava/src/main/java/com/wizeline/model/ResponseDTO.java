package com.wizeline.model;

public class ResponseDTO {
	
	private String status;
	private String codigo;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	private ErrorDTO errors = new ErrorDTO();
	
	public ErrorDTO getErrors() {
		return errors;
	}
	
	public void setErrors(ErrorDTO errors) {
		this.errors = errors;
	}
	

}
