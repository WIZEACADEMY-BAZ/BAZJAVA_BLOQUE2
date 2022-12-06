package com.wizeline.baz.LearningSpring.model;

public class NotificacionesDTO {

    private String id;
    private String pushId;
    private String phone;
    private String email;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmai() {
        return email;
    }

    public void setEmai(String emai) {
        this.email = emai;
    }

    public NotificacionesDTO() {
    }

    public NotificacionesDTO(String id, String pushId, String phone, String email) {
        this.id = id;
        this.pushId = pushId;
        this.phone = phone;
        this.email = email;
    }

    @Override
    public String toString() {
        return "NotificacionesDTO{" +
                "id='" + id + '\'' +
                ", pushId='" + pushId + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
