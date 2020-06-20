package com.example.pokdex.pokemon.types;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Type {
    public String getName() {
        return name;
    }
    @SerializedName("name")
    @Expose
    private String name;

    public Type(String name){
        this.name = name;
    }
}
