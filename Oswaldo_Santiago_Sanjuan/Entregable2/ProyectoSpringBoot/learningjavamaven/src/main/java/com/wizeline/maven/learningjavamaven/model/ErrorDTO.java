package com.wizeline.maven.learningjavamaven.model;

public class ErrorDTO {
    String errorCode;
    String message;

    public ErrorDTO(ErrorDTOBuilder bilder) {
       this.errorCode = bilder.errorCode;
       this.errorCode = bilder.message;
    }
    public ErrorDTO() {
        super();
    }
    public String getErrorCode() {
        return errorCode;
    }
    public String getMessage() {
        return message;
    }
    public static class ErrorDTOBuilder{
        private String errorCode;
        private String message;
        public ErrorDTOBuilder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }
        public ErrorDTOBuilder message(String message){
            this.message = message;
            return this;
        }
        public ErrorDTO build(){
            return new ErrorDTO(this);
        }
    }
}