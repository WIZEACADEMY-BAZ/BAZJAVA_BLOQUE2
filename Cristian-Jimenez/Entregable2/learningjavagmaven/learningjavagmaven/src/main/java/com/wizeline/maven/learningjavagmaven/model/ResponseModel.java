package com.wizeline.maven.learningjavagmaven.model;

public class ResponseModel {
    /**
     * Attribute status: Guarda el resultado del request, posibles valores success o fail.
     */
    private String status;
    /**
     * Attribute code: Guarda el codigo de la operacion realizada o error.
     */
    private String code;

    /**
     * Attribute error: Bean que maneja un listado de errores presentados en durante el procesamiento de request.
     */
    private ErrorModel errors = new ErrorModel();

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

    public ErrorModel getErrors() {
        return errors;
    }

    public void setErrors(ErrorModel errors) {
        this.errors = errors;
    }
}