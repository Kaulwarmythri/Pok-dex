package com.example.pokdex;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokdex.pokemon.pokemon;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavPokemonAdapter extends RecyclerView.Adapter<FavPokemonAdapter.ViewHolder>{
    private Context mContext;
    private ArrayList<pokemon> mPokemonList;
    private Cursor mCursor;

    public FavPokemonAdapter(Cursor cursor){
        mCursor = cursor;
    }
    public FavPokemonAdapter(Context context,ArrayList<FavPokemon> favPokemonList,ArrayList<pokemon> pokemonList){
        mContext = context;
        mPokemonList = pokemonList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fav_pokemon_view,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mPokemonList = new ArrayList<>();
        pokemon currentPokemon = mPokemonList.get(position);
        long id = mCursor.getLong(mCursor.getColumnIndex(FavouritePokemonContract.FavouritePokemonEntry._ID));
        holder.itemView.setTag(id);
        holder.fav_pokemon_name.setText(currentPokemon.getName());
        Picasso.get()
                .load(currentPokemon.getImageURL())
                .fit()
                .centerCrop()
                .into(holder.fav_pokemon_image);
        holder.fav_pokemon_baseExperience.setText(currentPokemon.getBase_experience());
        for (int i=0;i<currentPokemon.getTypesList().size();i++){
            holder.fav_pokemon_type.setText("Base-experience: " + currentPokemon.getTypesList().get(i).getType().getName());
        }
        for (int j=0;j<currentPokemon.getMovesList().size();j++){
            holder.fav_pokemon_move.setText("Moves: " + currentPokemon.getMovesList().get(j).getMove().getName());
        }
        for (int k=0;k<currentPokemon.getStatList().size();k++){
            holder.fav_pokemon_stat.setText("Base stat :" + currentPokemon.getStatList().get(k).getBaseStat() + "\n" +
               "Stats: " + currentPokemon.getStatList().get(k).getStat().getName());
        }
        holder.fav_pokemon_share.setOnClickListener(v -> {
            Intent shareTo = new Intent(Intent.ACTION_SEND);
            shareTo.putExtra(Intent.EXTRA_TEXT,"Pokemon Name: " + currentPokemon.getName());
            shareTo.putExtra(Intent.EXTRA_TEXT,
                    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + position + 1 + ".png");
            for (int i=0;i<currentPokemon.getTypesList().size();i++){
            shareTo.putExtra(Intent.EXTRA_TEXT,"Type: " + currentPokemon.getTypesList().get(i).getType().getName() );
            }
           shareTo.putExtra(Intent.EXTRA_TEXT,"BaseExperience: " + currentPokemon.getBase_experience());
            for (int j=0;j<currentPokemon.getMovesList().size();j++){
            shareTo.putExtra(Intent.EXTRA_TEXT,"Moves: "+ currentPokemon.getMovesList().get(j).getMove().getName());
            }
            for (int k=0;k<currentPokemon.getStatList().size();k++){
                shareTo.putExtra(Intent.EXTRA_TEXT,"Stats: " + currentPokemon.getStatList().get(k).getBaseStat() + "\n"
                        + currentPokemon.getStatList().get(k).getStat().getName() );
            }
            shareTo.setType("text/plain");
            mContext.startActivity(Intent.createChooser(shareTo, "Share via"));
        });

    }

    @Override
    public int getItemCount() {
        return mPokemonList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fav_pokemon_image;
        TextView fav_pokemon_name;
        TextView fav_pokemon_baseExperience;
        TextView fav_pokemon_type;
        TextView fav_pokemon_move;
        TextView fav_pokemon_stat;
        Button fav_pokemon_share;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            fav_pokemon_image = itemView.findViewById(R.id.fav_pokemon_image);
            fav_pokemon_name = itemView.findViewById(R.id.fav_pokemon_name);
            fav_pokemon_baseExperience = itemView.findViewById(R.id.fav_pokemon_baseExperience);
            fav_pokemon_type = itemView.findViewById(R.id.fav_pokemon_type);
            fav_pokemon_move = itemView.findViewById(R.id.fav_pokemon_move);
            fav_pokemon_stat = itemView.findViewById(R.id.fav_pokemon_stat);
            fav_pokemon_share = itemView.findViewById(R.id.fav_pokemon_share);

        }
    }
}
