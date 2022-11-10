package com.cursojava.proyecto.model;

public class ErrorDTO {
    String errorCode;
    String message;


    public ErrorDTO() {
        this.errorCode="";
        this.message="";
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
