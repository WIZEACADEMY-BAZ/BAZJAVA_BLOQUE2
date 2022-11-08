package com.wizeline.maven.learningjavagmaven.model;

public class ErrorModel {
    /**
     ** Attribute errorCode: Save error code id
     */
    String errorCode;
    /**
     * Attribute message: Save error message
     */
    String message;


    public ErrorModel() {
        super();
    }

    public ErrorModel(String errorCode, String message) {
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