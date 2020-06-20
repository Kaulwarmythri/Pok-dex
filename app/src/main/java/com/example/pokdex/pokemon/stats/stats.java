package com.example.pokdex.pokemon.stats;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class stats {
    @SerializedName("base_stat")
    @Expose
    private String baseStat;
    @SerializedName("stat")
    @Expose
    private stat stat;

    public stats(String baseStat,stat stat){
        this.baseStat = baseStat;
        this.stat = stat;

    }

    public String getBaseStat() {
        return baseStat;
    }
    public stat getStat() {
        return stat;
    }
}
