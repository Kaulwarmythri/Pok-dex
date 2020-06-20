package com.example.pokdex.evolutionChain.Chain;

import com.example.pokdex.evolutionChain.Chain.evolvesTo1.evolvesTo1;
import com.example.pokdex.evolutionChain.Chain.evolvesTo1.evolvesTo2.Species;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Chain {
    @SerializedName("evolves_to")
    @Expose
    private ArrayList<evolvesTo1> evolves_to;
    @SerializedName("species")
    @Expose
    private Species3 species;

    public Chain(Species3 species,ArrayList<evolvesTo1> evolves_to){
        this.species = species;
        this.evolves_to = evolves_to;
    }

    public ArrayList<evolvesTo1> getEvolves_to() {
        return evolves_to;
    }
    public Species3 getSpecies() {
        return species;
    }

    @Override
    public String toString() {
        return "Chain{" +
                "evolves_to=" + evolves_to +
                ", species=" + species +
                '}';
    }
}
