package com.example.pokdex.pokemon.moves;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class moves {
    @SerializedName("move")
    @Expose
    private Move move;

    public moves(Move move){
        this.move = move;
    }

    public Move getMove() {
        return move;
    }
    @Override
    public String toString() {
        return "moves{" +
                "move=" + move +
                '}';
    }
}
