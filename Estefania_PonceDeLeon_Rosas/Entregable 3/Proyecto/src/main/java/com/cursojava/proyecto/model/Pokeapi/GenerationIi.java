package com.cursojava.proyecto.model.Pokeapi;

import com.fasterxml.jackson.annotation.*;

public class GenerationIi {
    private Crystal crystal;
    private Gold gold;
    private Gold silver;

    @JsonProperty("crystal")
    public Crystal getCrystal() { return crystal; }
    @JsonProperty("crystal")
    public void setCrystal(Crystal value) { this.crystal = value; }

    @JsonProperty("gold")
    public Gold getGold() { return gold; }
    @JsonProperty("gold")
    public void setGold(Gold value) { this.gold = value; }

    @JsonProperty("silver")
    public Gold getSilver() { return silver; }
    @JsonProperty("silver")
    public void setSilver(Gold value) { this.silver = value; }
}
