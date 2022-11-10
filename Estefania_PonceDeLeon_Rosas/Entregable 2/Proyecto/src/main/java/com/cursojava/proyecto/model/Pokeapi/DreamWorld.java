package com.cursojava.proyecto.model.Pokeapi;

import com.fasterxml.jackson.annotation.*;

public class DreamWorld {
    private String frontDefault;
    private Object frontFemale;

    @JsonProperty("front_default")
    public String getFrontDefault() { return frontDefault; }
    @JsonProperty("front_default")
    public void setFrontDefault(String value) { this.frontDefault = value; }

    @JsonProperty("front_female")
    public Object getFrontFemale() { return frontFemale; }
    @JsonProperty("front_female")
    public void setFrontFemale(Object value) { this.frontFemale = value; }
}
