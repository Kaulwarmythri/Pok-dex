package com.example.pokdex.types.Pokemon;

import com.example.pokdex.pokemon.pokemon;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pokemon {
    @SerializedName("name")
    @Expose
    private pokemonName pokemon;
    public Pokemon(pokemonName pokemon){
        this.pokemon = pokemon;
    }

    public pokemonName getPokemon() {
        return pokemon;
    }


    @Override
    public String toString() {
        return "Pokemon{" +
                "pokemon=" + pokemon +
                '}';
    }
}
