package com.wizeline.maven.learningjavamaven.model;

import com.wizeline.maven.learningjavamaven.patterns.ClientModel;

import java.util.List;

public class ResponseModel {
    private String status;
    private String code;

    private ErrorModel errors = new ErrorModel();

    private UserModel userModel = new UserModel();

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

    public UserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(UserModel userModel) {
        this.userModel = userModel;
    }
}
