package com.wizeline.maven.learningjavamaven.model;

public class ResponseDTO {
  private String status;
  private String code;
  private ErrorDTO errors = new ErrorDTO();

  public void setStatus(String status) {
    this.status = status;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public void setErrors(ErrorDTO errors) {
    this.errors = errors;
  }

}
