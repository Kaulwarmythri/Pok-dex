package com.example.pokdex.pokemon.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Move {
    @SerializedName("name")
    @Expose
    private String name;

    public Move(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return "Move{" +
                "name='" + name + '\'' +
                '}';
    }
}
