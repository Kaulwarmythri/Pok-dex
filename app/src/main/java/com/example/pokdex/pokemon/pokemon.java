package com.example.pokdex.pokemon;

import com.example.pokdex.pokemon.moves.moves;
import com.example.pokdex.pokemon.stats.stats;
import com.example.pokdex.pokemon.types.types;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class pokemon {
    @SerializedName("base_experience")
    @Expose
    private String base_experience;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("moves")
    @Expose
    private ArrayList<moves> movesList;
    @SerializedName("types")
    @Expose
    private ArrayList<types> typesList;
    @SerializedName("stats")
    @Expose
    private ArrayList<stats> statList;
    private String imageURL;
    public String fav_status;
    private boolean expanded;

    public pokemon(String name, String imageURL,ArrayList<moves> movesList,ArrayList<types> typesList,ArrayList<stats> statsList) {
        this.name = name;
        this.imageURL = imageURL;
        this.typesList = typesList;
        this.statList = statsList;
        this.movesList = movesList;
        this.expanded = false;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public String getBase_experience() {
        return base_experience;
    }

    public String getName() {
        return name;
    }
    public ArrayList<moves> getMovesList() {
        return movesList;
    }
    public ArrayList<types> getTypesList() {
        return typesList;
    }
    public ArrayList<stats> getStatList() {
        return statList;
    }
    public String getImageURL(){
        return imageURL;
    }

    public String getFav_status() {
        return fav_status;
    }
    public void setFav_status(String fav_status) {
        this.fav_status = fav_status;
    }
}
