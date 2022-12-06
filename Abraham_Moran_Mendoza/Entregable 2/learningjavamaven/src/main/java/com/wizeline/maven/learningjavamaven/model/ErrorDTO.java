package com.wizeline.maven.learningjavamaven.model;

public class ErrorDTO {
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
}
