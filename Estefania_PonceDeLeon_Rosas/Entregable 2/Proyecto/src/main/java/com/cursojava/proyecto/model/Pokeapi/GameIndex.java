package com.cursojava.proyecto.model.Pokeapi;

import com.fasterxml.jackson.annotation.*;

public class GameIndex {
    private long gameIndex;
    private Species version;

    @JsonProperty("game_index")
    public long getGameIndex() { return gameIndex; }
    @JsonProperty("game_index")
    public void setGameIndex(long value) { this.gameIndex = value; }

    @JsonProperty("version")
    public Species getVersion() { return version; }
    @JsonProperty("version")
    public void setVersion(Species value) { this.version = value; }
}
