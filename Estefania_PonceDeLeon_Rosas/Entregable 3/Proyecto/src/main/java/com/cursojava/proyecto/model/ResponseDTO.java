package com.cursojava.proyecto.model;

import org.springframework.http.HttpStatus;

public class ResponseDTO {
    private String status;
    private String code;
    private HttpStatus httpStatus;
    private ErrorDTO errors;


    public ResponseDTO(){
        this.status="success";
        this.code="OK000";
        this.errors= new ErrorDTO();
    }

    public ResponseDTO(String status){
        this.status=status;
    }


    public ResponseDTO(String status, HttpStatus httpStatus){
        this.status=status;
        this.httpStatus=httpStatus;
    }


    public void setErrors(ErrorDTO errors) {
        this.errors = errors;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
