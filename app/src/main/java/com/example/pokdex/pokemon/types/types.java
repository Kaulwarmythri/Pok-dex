package com.example.pokdex.pokemon.types;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class types {
    @SerializedName("type")
    @Expose
    private Type type;

    public types(Type type){
        this.type = type;
    }

    public Type getType() {
        return type;
    }
    @Override
    public String toString() {
        return "types{" +
                "type=" + type +
                '}';
    }
}
