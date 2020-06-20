package com.example.pokdex.types.Pokemon;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class pokemonName {
    @SerializedName("name")
    @Expose
    private String name;

    public pokemonName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "pokemonName{" +
                "name='" + name + '\'' +
                '}';
    }
}
