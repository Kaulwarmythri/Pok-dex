package com.example.pokdex.types;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.pokdex.types.Pokemon.Pokemon;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class types {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("pokemon")
    @Expose
    private ArrayList<Pokemon> pokemonList;

    public boolean expanded;

    public types(String name, ArrayList<Pokemon> pokemonList){
        this.pokemonList = pokemonList;
        this.name = name;
        this.expanded = false;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getName() {
        return name;
    }
    public ArrayList<Pokemon> getPokemonList() {
        return pokemonList;
    }

    @Override
    public String toString() {
        return "types{" +
                "name='" + name + '\'' +
                ", pokemonList=" + pokemonList +
                '}';
    }

}
