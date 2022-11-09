package com.cursojava.proyecto.model.Pokeapi;

import com.fasterxml.jackson.annotation.*;

public class Emerald {
    private String frontDefault;
    private String frontShiny;

    @JsonProperty("front_default")
    public String getFrontDefault() { return frontDefault; }
    @JsonProperty("front_default")
    public void setFrontDefault(String value) { this.frontDefault = value; }

    @JsonProperty("front_shiny")
    public String getFrontShiny() { return frontShiny; }
    @JsonProperty("front_shiny")
    public void setFrontShiny(String value) { this.frontShiny = value; }
}
