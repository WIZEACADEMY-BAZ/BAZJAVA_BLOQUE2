package com.cursojava.proyecto.model.Pokeapi;

import com.fasterxml.jackson.annotation.*;

public class Type {
    private long slot;
    private Species type;

    @JsonProperty("slot")
    public long getSlot() { return slot; }
    @JsonProperty("slot")
    public void setSlot(long value) { this.slot = value; }

    @JsonProperty("type")
    public Species getType() { return type; }
    @JsonProperty("type")
    public void setType(Species value) { this.type = value; }
}
