package com.example.pokdex.pokemon.stats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class stat {
    @SerializedName("name")
    @Expose
    private String name;

    public stat(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
