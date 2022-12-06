package com.cursojava.proyecto.model.Pokeapi;

import com.fasterxml.jackson.annotation.*;
import java.util.Map;

public class Versions {
    private GenerationI generationI;
    private GenerationIi generationIi;
    private GenerationIii generationIii;
    private GenerationIv generationIv;
    private GenerationV generationV;
    private Map<String, Home> generationVi;
    private GenerationVii generationVii;
    private GenerationViii generationViii;

    @JsonProperty("generation-i")
    public GenerationI getGenerationI() { return generationI; }
    @JsonProperty("generation-i")
    public void setGenerationI(GenerationI value) { this.generationI = value; }

    @JsonProperty("generation-ii")
    public GenerationIi getGenerationIi() { return generationIi; }
    @JsonProperty("generation-ii")
    public void setGenerationIi(GenerationIi value) { this.generationIi = value; }

    @JsonProperty("generation-iii")
    public GenerationIii getGenerationIii() { return generationIii; }
    @JsonProperty("generation-iii")
    public void setGenerationIii(GenerationIii value) { this.generationIii = value; }

    @JsonProperty("generation-iv")
    public GenerationIv getGenerationIv() { return generationIv; }
    @JsonProperty("generation-iv")
    public void setGenerationIv(GenerationIv value) { this.generationIv = value; }

    @JsonProperty("generation-v")
    public GenerationV getGenerationV() { return generationV; }
    @JsonProperty("generation-v")
    public void setGenerationV(GenerationV value) { this.generationV = value; }

    @JsonProperty("generation-vi")
    public Map<String, Home> getGenerationVi() { return generationVi; }
    @JsonProperty("generation-vi")
    public void setGenerationVi(Map<String, Home> value) { this.generationVi = value; }

    @JsonProperty("generation-vii")
    public GenerationVii getGenerationVii() { return generationVii; }
    @JsonProperty("generation-vii")
    public void setGenerationVii(GenerationVii value) { this.generationVii = value; }

    @JsonProperty("generation-viii")
    public GenerationViii getGenerationViii() { return generationViii; }
    @JsonProperty("generation-viii")
    public void setGenerationViii(GenerationViii value) { this.generationViii = value; }
}
