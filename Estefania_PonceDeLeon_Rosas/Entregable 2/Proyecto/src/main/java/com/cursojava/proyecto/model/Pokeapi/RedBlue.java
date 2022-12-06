package com.cursojava.proyecto.model.Pokeapi;

import com.fasterxml.jackson.annotation.*;

public class RedBlue {
    private String backDefault;
    private String backGray;
    private String backTransparent;
    private String frontDefault;
    private String frontGray;
    private String frontTransparent;

    @JsonProperty("back_default")
    public String getBackDefault() { return backDefault; }
    @JsonProperty("back_default")
    public void setBackDefault(String value) { this.backDefault = value; }

    @JsonProperty("back_gray")
    public String getBackGray() { return backGray; }
    @JsonProperty("back_gray")
    public void setBackGray(String value) { this.backGray = value; }

    @JsonProperty("back_transparent")
    public String getBackTransparent() { return backTransparent; }
    @JsonProperty("back_transparent")
    public void setBackTransparent(String value) { this.backTransparent = value; }

    @JsonProperty("front_default")
    public String getFrontDefault() { return frontDefault; }
    @JsonProperty("front_default")
    public void setFrontDefault(String value) { this.frontDefault = value; }

    @JsonProperty("front_gray")
    public String getFrontGray() { return frontGray; }
    @JsonProperty("front_gray")
    public void setFrontGray(String value) { this.frontGray = value; }

    @JsonProperty("front_transparent")
    public String getFrontTransparent() { return frontTransparent; }
    @JsonProperty("front_transparent")
    public void setFrontTransparent(String value) { this.frontTransparent = value; }
}
