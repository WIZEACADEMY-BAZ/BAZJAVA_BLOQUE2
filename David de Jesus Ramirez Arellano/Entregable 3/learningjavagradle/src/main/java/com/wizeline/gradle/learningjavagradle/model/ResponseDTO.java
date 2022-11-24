package com.wizeline.gradle.learningjavagradle.model;

public class ResponseDTO {

    private String status;

    private String code;
    
    public ResponseDTO() {
		super();
	}

	public ResponseDTO(String status, String code) {
		super();
		this.status = status;
		this.code = code;
	}

	private ErrorDTO errors = new ErrorDTO();

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
}
