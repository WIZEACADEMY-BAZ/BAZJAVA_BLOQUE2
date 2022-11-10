package com.baz.wizeline.learningspring.model;

public class ImagesAPIDTO {

    private String image;

    public ImagesAPIDTO() {
    }

    public ImagesAPIDTO(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
