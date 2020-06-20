package com.example.pokdex.evolutionChain.Chain.evolvesTo1;

import com.example.pokdex.evolutionChain.Chain.evolvesTo1.evolvesTo2.evolvesTo2;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class evolvesTo1 {
    @SerializedName("evolves_to")
    @Expose
    private ArrayList<evolvesTo2> evolvesTo;

    @SerializedName("species")
    @Expose
    private Species2 species;

    public evolvesTo1(ArrayList<evolvesTo2> evolvesTo,Species2 species){
        this.evolvesTo = evolvesTo;
        this.species = species;

    }

    public ArrayList<evolvesTo2> getEvolvesTo() {
        return evolvesTo;
    }
    public Species2 getSpecies() {
        return species;
    }
    @Override
    public String toString() {
        return "evolvesTo1{" +
                "evolvesTo=" + evolvesTo +
                ", species=" + species +
                '}';
    }
}
