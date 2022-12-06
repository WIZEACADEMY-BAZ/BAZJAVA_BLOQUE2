package com.cursojava.proyecto.model.Pokeapi;

import com.fasterxml.jackson.annotation.*;

public class Gold {
    private String backDefault;
    private String backShiny;
    private String frontDefault;
    private String frontShiny;
    private String frontTransparent;

    @JsonProperty("back_default")
    public String getBackDefault() { return backDefault; }
    @JsonProperty("back_default")
    public void setBackDefault(String value) { this.backDefault = value; }

    @JsonProperty("back_shiny")
    public String getBackShiny() { return backShiny; }
    @JsonProperty("back_shiny")
    public void setBackShiny(String value) { this.backShiny = value; }

    @JsonProperty("front_default")
    public String getFrontDefault() { return frontDefault; }
    @JsonProperty("front_default")
    public void setFrontDefault(String value) { this.frontDefault = value; }

    @JsonProperty("front_shiny")
    public String getFrontShiny() { return frontShiny; }
    @JsonProperty("front_shiny")
    public void setFrontShiny(String value) { this.frontShiny = value; }

    @JsonProperty("front_transparent")
    public String getFrontTransparent() { return frontTransparent; }
    @JsonProperty("front_transparent")
    public void setFrontTransparent(String value) { this.frontTransparent = value; }
}
