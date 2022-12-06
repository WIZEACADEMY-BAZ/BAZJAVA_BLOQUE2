package com.cursojava.proyecto.model.Pokeapi;

import com.fasterxml.jackson.annotation.*;

public class GenerationViii {
    private DreamWorld icons;

    @JsonProperty("icons")
    public DreamWorld getIcons() { return icons; }
    @JsonProperty("icons")
    public void setIcons(DreamWorld value) { this.icons = value; }
}
