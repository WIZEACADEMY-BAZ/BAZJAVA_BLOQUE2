package com.cursojava.proyecto.model.Pokeapi;

import com.fasterxml.jackson.annotation.*;

public class Home {
    private String frontDefault;
    private Object frontFemale;
    private String frontShiny;
    private Object frontShinyFemale;

    @JsonProperty("front_default")
    public String getFrontDefault() { return frontDefault; }
    @JsonProperty("front_default")
    public void setFrontDefault(String value) { this.frontDefault = value; }

    @JsonProperty("front_female")
    public Object getFrontFemale() { return frontFemale; }
    @JsonProperty("front_female")
    public void setFrontFemale(Object value) { this.frontFemale = value; }

    @JsonProperty("front_shiny")
    public String getFrontShiny() { return frontShiny; }
    @JsonProperty("front_shiny")
    public void setFrontShiny(String value) { this.frontShiny = value; }

    @JsonProperty("front_shiny_female")
    public Object getFrontShinyFemale() { return frontShinyFemale; }
    @JsonProperty("front_shiny_female")
    public void setFrontShinyFemale(Object value) { this.frontShinyFemale = value; }
}
