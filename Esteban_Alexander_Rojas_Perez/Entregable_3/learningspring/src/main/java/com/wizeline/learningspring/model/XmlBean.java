package com.wizeline.learningspring.model;

public class XmlBean {
    private Object data;

    public XmlBean() {
    }

    public XmlBean(Object data) {
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object object) {
        this.data = object;
    }
}
