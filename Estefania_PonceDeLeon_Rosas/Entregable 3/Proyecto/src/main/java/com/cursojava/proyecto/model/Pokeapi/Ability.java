package com.cursojava.proyecto.model.Pokeapi;

import com.fasterxml.jackson.annotation.*;

public class Ability {
    private Species ability;
    private boolean isHidden;
    private long slot;

    @JsonProperty("ability")
    public Species getAbility() { return ability; }
    @JsonProperty("ability")
    public void setAbility(Species value) { this.ability = value; }

    @JsonProperty("is_hidden")
    public boolean getIsHidden() { return isHidden; }
    @JsonProperty("is_hidden")
    public void setIsHidden(boolean value) { this.isHidden = value; }

    @JsonProperty("slot")
    public long getSlot() { return slot; }
    @JsonProperty("slot")
    public void setSlot(long value) { this.slot = value; }
}
