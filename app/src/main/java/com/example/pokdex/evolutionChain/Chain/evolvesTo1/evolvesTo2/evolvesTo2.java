package com.example.pokdex.evolutionChain.Chain.evolvesTo1.evolvesTo2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class evolvesTo2 {
    @SerializedName("species")
    @Expose
    private Species species;

    public evolvesTo2(Species species){
        this.species = species;

    }

    public Species getSpecies() {
        return species;
    }
    @Override
    public String toString() {
        return "evolvesTo2{" +
                "species=" + species +
                '}';
    }
}
