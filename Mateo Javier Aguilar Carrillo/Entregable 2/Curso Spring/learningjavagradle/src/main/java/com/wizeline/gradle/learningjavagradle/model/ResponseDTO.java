package com.wizeline.gradle.learningjavagradle.model;

public class ResponseDTO {
	private String status;
	private String code;
	
	private ErrorDTO errors = new ErrorDTO();
	
	public ResponseDTO() {
		
	}
	
	public ResponseDTO(String status, String code, ErrorDTO errors) {
		this.status = status;
		this.code = code;
		this.errors = errors;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCode() {
		return code;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public ErrorDTO getErrors() {
		return errors;
	}
	
	public void setErrors(ErrorDTO errors) {
		this.errors = errors;
	}
	
	public static class ErrorDTO {
		
	    String errorCode;
	    String message;
	    
	    public ErrorDTO() {
			super();
		}

		public ErrorDTO(String errorCode, String message) {
			super();
			this.errorCode = errorCode;
			this.message = message;
		}

		public String getErrorCode() {
	        return errorCode;
	    }

	    public void setErrorCode(String errorCode) {
	        this.errorCode = errorCode;
	    }

	    public String getMessage() {
	        return message;
	    }

	    public void setMessage(String message) {
	        this.message = message;
	    }
	}

}
