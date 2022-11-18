package com.wizeline.maven.learninjavamaven.model;

public class ResponseDTO {

    private String status;

    private String code;


    private ErrorDTO errores = new ErrorDTO();

    public String getStatus(){return status;}

    public void setStatus(String status){this.status = status;}

    public String getCode(){return code;}

    public void setCode(String code){this.code = code;}

    public ErrorDTO getErrores(){return errores;}

    public void  setErrores(ErrorDTO errores){this.errores = errores;}
}
