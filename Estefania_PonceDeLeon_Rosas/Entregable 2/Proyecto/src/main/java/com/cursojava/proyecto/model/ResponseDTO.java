package com.cursojava.proyecto.model;

public class ResponseDTO {
    private String status;
    private String code;
    private ErrorDTO errors;


    public ResponseDTO(){
        this.status="success";
        this.code="OK000";
        this.errors= new ErrorDTO();
    }

    public ResponseDTO(String status){
        this.status=status;
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


}
