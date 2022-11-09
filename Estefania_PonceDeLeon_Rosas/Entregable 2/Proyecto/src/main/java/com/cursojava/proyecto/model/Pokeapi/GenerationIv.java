package com.cursojava.proyecto.model.Pokeapi;

import com.fasterxml.jackson.annotation.*;

public class GenerationIv {
    private Sprites diamondPearl;
    private Sprites heartgoldSoulsilver;
    private Sprites platinum;

    @JsonProperty("diamond-pearl")
    public Sprites getDiamondPearl() { return diamondPearl; }
    @JsonProperty("diamond-pearl")
    public void setDiamondPearl(Sprites value) { this.diamondPearl = value; }

    @JsonProperty("heartgold-soulsilver")
    public Sprites getHeartgoldSoulsilver() { return heartgoldSoulsilver; }
    @JsonProperty("heartgold-soulsilver")
    public void setHeartgoldSoulsilver(Sprites value) { this.heartgoldSoulsilver = value; }

    @JsonProperty("platinum")
    public Sprites getPlatinum() { return platinum; }
    @JsonProperty("platinum")
    public void setPlatinum(Sprites value) { this.platinum = value; }
}
