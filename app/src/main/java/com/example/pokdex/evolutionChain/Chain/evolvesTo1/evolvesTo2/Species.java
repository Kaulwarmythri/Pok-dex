package com.example.pokdex.evolutionChain.Chain.evolvesTo1.evolvesTo2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Species {
    @SerializedName("name")
    @Expose
    private String name;
    public Species(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "Species{" +
                "name='" + name + '\'' +
                '}';
    }
}
