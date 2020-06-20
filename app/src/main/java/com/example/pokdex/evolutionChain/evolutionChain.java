package com.example.pokdex.evolutionChain;

import com.example.pokdex.evolutionChain.Chain.Chain;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class evolutionChain {
    @SerializedName("chain")
    @Expose
    public Chain chain;

    public evolutionChain(Chain chain){
        this.chain = chain;
    }

    public Chain getChain() {
        return chain;
    }
    @Override
    public String toString() {
        return "evolutionChain{" +
                "chain=" + chain +
                '}';
    }
}
