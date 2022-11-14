package com.baz.wizeline.learningspring.model;

public class AnswerAPIDTO extends ImagesAPIDTO{

    private String answer;

    private boolean forced;

    public AnswerAPIDTO() {
    }

    public AnswerAPIDTO(String answer, boolean forced) {
        this.answer = answer;
        this.forced = forced;
    }

    public AnswerAPIDTO(String image, String answer, boolean forced) {
        super(image);
        this.answer = answer;
        this.forced = forced;
    }

}
