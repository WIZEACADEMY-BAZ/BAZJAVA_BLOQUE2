package com.wizeline.learningspring.model.herencia;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class UserBreaches extends UserBreachesDetails{
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Title")
    private String title;
    @JsonProperty("Domain")
    private String domain;
    @JsonProperty("BreachDate")
    private String breachDate;
    @JsonProperty("AddedDate")
    private String addedDate;
    @JsonProperty("ModifiedDate")
    private String modifiedDate;

    public UserBreaches() {
        super();
    }

    public UserBreaches(String name, String title, String domain, String breachDate, String addedDate, String modifiedDate) {
        this.name = name;
        this.title = title;
        this.domain = domain;
        this.breachDate = breachDate;
        this.addedDate = addedDate;
        this.modifiedDate = modifiedDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getBreachDate() {
        return breachDate;
    }

    public void setBreachDate(String breachDate) {
        this.breachDate = breachDate;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
