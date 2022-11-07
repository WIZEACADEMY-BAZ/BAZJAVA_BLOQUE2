package com.cursojava.proyecto.model.Pokeapi;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class PokeApiPokemon {
    private List<Ability> abilities;
    private long baseExperience;
    private List<Species> forms;
    private List<GameIndex> gameIndices;
    private long height;
    private List<HeldItem> heldItems;
    private long id;
    private boolean isDefault;
    private String locationAreaEncounters;
    private List<Move> moves;
    private String name;
    private long order;
    private List<Object> pastTypes;
    private Species species;
    private Sprites sprites;
    private List<Stat> stats;
    private List<Type> types;
    private long weight;

    @JsonProperty("abilities")
    public List<Ability> getAbilities() { return abilities; }
    @JsonProperty("abilities")
    public void setAbilities(List<Ability> value) { this.abilities = value; }

    @JsonProperty("base_experience")
    public long getBaseExperience() { return baseExperience; }
    @JsonProperty("base_experience")
    public void setBaseExperience(long value) { this.baseExperience = value; }

    @JsonProperty("forms")
    public List<Species> getForms() { return forms; }
    @JsonProperty("forms")
    public void setForms(List<Species> value) { this.forms = value; }

    @JsonProperty("game_indices")
    public List<GameIndex> getGameIndices() { return gameIndices; }
    @JsonProperty("game_indices")
    public void setGameIndices(List<GameIndex> value) { this.gameIndices = value; }

    @JsonProperty("height")
    public long getHeight() { return height; }
    @JsonProperty("height")
    public void setHeight(long value) { this.height = value; }

    @JsonProperty("held_items")
    public List<HeldItem> getHeldItems() { return heldItems; }
    @JsonProperty("held_items")
    public void setHeldItems(List<HeldItem> value) { this.heldItems = value; }

    @JsonProperty("id")
    public long getID() { return id; }
    @JsonProperty("id")
    public void setID(long value) { this.id = value; }

    @JsonProperty("is_default")
    public boolean getIsDefault() { return isDefault; }
    @JsonProperty("is_default")
    public void setIsDefault(boolean value) { this.isDefault = value; }

    @JsonProperty("location_area_encounters")
    public String getLocationAreaEncounters() { return locationAreaEncounters; }
    @JsonProperty("location_area_encounters")
    public void setLocationAreaEncounters(String value) { this.locationAreaEncounters = value; }

    @JsonProperty("moves")
    public List<Move> getMoves() { return moves; }
    @JsonProperty("moves")
    public void setMoves(List<Move> value) { this.moves = value; }

    @JsonProperty("name")
    public String getName() { return name; }
    @JsonProperty("name")
    public void setName(String value) { this.name = value; }

    @JsonProperty("order")
    public long getOrder() { return order; }
    @JsonProperty("order")
    public void setOrder(long value) { this.order = value; }

    @JsonProperty("past_types")
    public List<Object> getPastTypes() { return pastTypes; }
    @JsonProperty("past_types")
    public void setPastTypes(List<Object> value) { this.pastTypes = value; }

    @JsonProperty("species")
    public Species getSpecies() { return species; }
    @JsonProperty("species")
    public void setSpecies(Species value) { this.species = value; }

    @JsonProperty("sprites")
    public Sprites getSprites() { return sprites; }
    @JsonProperty("sprites")
    public void setSprites(Sprites value) { this.sprites = value; }

    @JsonProperty("stats")
    public List<Stat> getStats() { return stats; }
    @JsonProperty("stats")
    public void setStats(List<Stat> value) { this.stats = value; }

    @JsonProperty("types")
    public List<Type> getTypes() { return types; }
    @JsonProperty("types")
    public void setTypes(List<Type> value) { this.types = value; }

    @JsonProperty("weight")
    public long getWeight() { return weight; }
    @JsonProperty("weight")
    public void setWeight(long value) { this.weight = value; }
}
