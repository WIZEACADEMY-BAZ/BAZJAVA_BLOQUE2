package com.cursojava.proyecto.model.Pokeapi;

import com.fasterxml.jackson.annotation.*;

public class Stat {
    private long baseStat;
    private long effort;
    private Species stat;

    @JsonProperty("base_stat")
    public long getBaseStat() { return baseStat; }
    @JsonProperty("base_stat")
    public void setBaseStat(long value) { this.baseStat = value; }

    @JsonProperty("effort")
    public long getEffort() { return effort; }
    @JsonProperty("effort")
    public void setEffort(long value) { this.effort = value; }

    @JsonProperty("stat")
    public Species getStat() { return stat; }
    @JsonProperty("stat")
    public void setStat(Species value) { this.stat = value; }
}
