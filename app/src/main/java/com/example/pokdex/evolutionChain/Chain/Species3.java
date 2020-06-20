package com.example.pokdex.evolutionChain.Chain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Species3 {
    @SerializedName("name")
    @Expose
    private String name;
    public Species3(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "Species3{" +
                "name='" + name + '\'' +
                '}';
    }
}
