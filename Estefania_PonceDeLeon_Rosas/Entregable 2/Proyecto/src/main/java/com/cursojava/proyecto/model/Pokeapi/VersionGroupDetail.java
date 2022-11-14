package com.cursojava.proyecto.model.Pokeapi;

import com.fasterxml.jackson.annotation.*;

public class VersionGroupDetail {
    private long levelLearnedAt;
    private Species moveLearnMethod;
    private Species versionGroup;

    @JsonProperty("level_learned_at")
    public long getLevelLearnedAt() { return levelLearnedAt; }
    @JsonProperty("level_learned_at")
    public void setLevelLearnedAt(long value) { this.levelLearnedAt = value; }

    @JsonProperty("move_learn_method")
    public Species getMoveLearnMethod() { return moveLearnMethod; }
    @JsonProperty("move_learn_method")
    public void setMoveLearnMethod(Species value) { this.moveLearnMethod = value; }

    @JsonProperty("version_group")
    public Species getVersionGroup() { return versionGroup; }
    @JsonProperty("version_group")
    public void setVersionGroup(Species value) { this.versionGroup = value; }
}
