package com.example.pokdex;

public class FavPokemon {
    private String name;
    private  String url;
    private String baseExperience;
    private String type;
    private String move;
    private String stat;
    private String id;

    public FavPokemon(String id,String name,String url,String baseExperience,String type,String move,String stat){
        this.id = id;
        this.name = name;
        this.url = url;
        this.baseExperience = baseExperience;
        this.type = type;
        this.move = move;
        this.stat = stat;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getBaseExperience() {
        return baseExperience;
    }

    public String getType() {
        return type;
    }

    public String getMove() {
        return move;
    }

    public String getStat() {
        return stat;
    }
}
