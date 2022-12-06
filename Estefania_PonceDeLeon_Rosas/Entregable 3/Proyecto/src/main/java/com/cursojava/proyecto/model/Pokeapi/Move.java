package com.cursojava.proyecto.model.Pokeapi;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class Move {
    private Species move;
    private List<VersionGroupDetail> versionGroupDetails;

    @JsonProperty("move")
    public Species getMove() { return move; }
    @JsonProperty("move")
    public void setMove(Species value) { this.move = value; }

    @JsonProperty("version_group_details")
    public List<VersionGroupDetail> getVersionGroupDetails() { return versionGroupDetails; }
    @JsonProperty("version_group_details")
    public void setVersionGroupDetails(List<VersionGroupDetail> value) { this.versionGroupDetails = value; }
}
