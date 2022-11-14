package com.cursojava.proyecto.model.Pokeapi;

import com.fasterxml.jackson.annotation.*;
import java.util.List;

public class HeldItem {
    private Species item;
    private List<VersionDetail> versionDetails;

    @JsonProperty("item")
    public Species getItem() { return item; }
    @JsonProperty("item")
    public void setItem(Species value) { this.item = value; }

    @JsonProperty("version_details")
    public List<VersionDetail> getVersionDetails() { return versionDetails; }
    @JsonProperty("version_details")
    public void setVersionDetails(List<VersionDetail> value) { this.versionDetails = value; }
}
