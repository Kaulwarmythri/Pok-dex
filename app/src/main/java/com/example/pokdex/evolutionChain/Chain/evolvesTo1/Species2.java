package com.example.pokdex.evolutionChain.Chain.evolvesTo1;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Species2 {
    @SerializedName("name")
    @Expose
    private String name;

    public Species2(String name){
        this.name = name;

    }
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "Species2{" +
                "name='" + name + '\'' +
                '}';
    }
}
