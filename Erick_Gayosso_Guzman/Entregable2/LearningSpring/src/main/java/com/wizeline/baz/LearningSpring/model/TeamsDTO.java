package com.wizeline.baz.LearningSpring.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TeamsDTO {

    private String nameTeam;
    private String Group;

    public String getNameTeam() {
        return nameTeam;
    }

    public void setNameTeam(String nameTeam) {
        this.nameTeam = nameTeam;
    }

    public String getGroup() {
        return Group;
    }

    public void setGroup(String group) {
        Group = group;
    }

    @Override
    public String toString() {
        return "TeamsDTO{" +
                "nameTeam='" + nameTeam + '\'' +
                ", Group='" + Group + '\'' +
                '}';
    }
}
