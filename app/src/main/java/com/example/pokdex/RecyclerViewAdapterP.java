package com.example.pokdex;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokdex.pokemon.pokemon;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;


import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterP extends RecyclerView.Adapter<RecyclerViewAdapterP.ViewHolder> implements Filterable{
    private static final String TAG = "RecyclerViewAdapterP";
    private ArrayList<pokemon> mFormsList;
    private ArrayList<pokemon> mFormsListFull;
    private FavouritePokemonDbHelper dbHelper;
    private Cursor mCursor;
    Context mContext;

    public RecyclerViewAdapterP(Cursor cursor){
        mCursor = cursor;
    }

    public RecyclerViewAdapterP(Context context, ArrayList<pokemon> formsList){
        mContext = context;
        mFormsList = formsList;
        mFormsListFull = new ArrayList<>(mFormsList);

    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pokemon_view, parent, false);
        dbHelper = new FavouritePokemonDbHelper(mContext);
        SharedPreferences preferences = mContext.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = preferences.getBoolean("First Start", true);
        if (firstStart){
            createTableOnFirstStart();
        }
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: calling");
        mFormsList = new ArrayList<>();
        long id = mCursor.getLong(mCursor.getColumnIndex(FavouritePokemonContract.FavouritePokemonEntry._ID));
        pokemon currentForm = mFormsList.get(position);
        readCursorData(currentForm,holder);
        holder.itemView.setTag(id);//Just to use it in the fragment

        holder.namePokemon.setText(currentForm.getName());

        Picasso.get()
                .load(currentForm.getImageURL())
                .fit()
                .centerCrop()
                .into(holder.imagePokemon);
        int lastPosition = -1;
        holder.baseExperience.setText(currentForm.getBase_experience());
        for (int i=0;i<currentForm.getTypesList().size();i++){
        holder.types.setText(currentForm.getTypesList().get(i).getType().getName());
        }
        for (int j=0;j<currentForm.getMovesList().size();j++){
            holder.moves.setText(currentForm.getMovesList().get(j).getMove().getName());
        }
        for (int k=0;k<currentForm.getStatList().size();k++){
            holder.stats.setText("Base Stat: " + currentForm.getStatList().get(k).getBaseStat() + "\n" + "Stats: "
                    + currentForm.getStatList().get(k).getStat().getName());
        }
        holder.parentLayout.setAnimation(AnimationUtils.loadAnimation(mContext,
                (position > lastPosition)? R.anim.loading_down_anim : R.anim.loading_up_anim));
        boolean isExpanded = mFormsListFull.get(position).isExpanded();
        holder.expandableLayout.setVisibility((isExpanded)?View.VISIBLE:View.GONE);
        holder.parentLayout.setOnClickListener(v -> {
            pokemon pokemon = mFormsListFull.get(position);
            pokemon.setExpanded(!pokemon.isExpanded());
            notifyItemChanged(position);
        });
        holder.addToFav.setOnClickListener(v -> {
            pokemon pokemon = mFormsListFull.get(position);
            String type = "Type: ";
            String move = "Move: ";
            String stat = "Stat: ";
            if (pokemon.getFav_status().equals("0")){
                pokemon.setFav_status("1");
                for (int i=0;i<pokemon.getTypesList().size();i++){
                    type = pokemon.getTypesList().get(i).getType().getName() + "\n";
                }
                for(int j=0;j<pokemon.getMovesList().size();j++){
                    move = pokemon.getMovesList().get(j).getMove().getName() + "\n";
                }
                for (int k=0;k<pokemon.getStatList().size();k++){
                    stat = pokemon.getStatList().get(k).getStat().getName() + "\n";
                }
               dbHelper.insertValues(pokemon.getName(),type,stat,move,
                       pokemon.getBase_experience(),pokemon.getImageURL(),pokemon.getFav_status());
                holder.addToFav.setBackgroundResource(R.drawable.ic_add_to_favourite);
            }else{
                pokemon.setFav_status("0");
                dbHelper.removeData(id);
                holder.addToFav.setBackgroundResource(R.drawable.ic_favourite_grey);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mFormsList.size();
    }

    @Override
    public Filter getFilter() {
        return formsFilter;
    }

    private Filter formsFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<pokemon> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length()==0){
                filteredList.addAll(mFormsListFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (pokemon form : mFormsListFull){
                    if (form.getName().toLowerCase().contains(filterPattern)){
                        filteredList.add(form);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values=filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mFormsList.clear();
            mFormsListFull.addAll((List) results.values);
            notifyDataSetChanged();

        }
    };


    public static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView imagePokemon;
        TextView namePokemon;
        CardView parentLayout;
        LinearLayout expandableLayout;
        TextView baseExperience;
        TextView stats;
        TextView moves;
        TextView types;
        Button addToFav;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePokemon = itemView.findViewById(R.id.image_pokemon);
            namePokemon = itemView.findViewById(R.id.name_pokemon);
            addToFav = itemView.findViewById(R.id.buttonFavourite);
            parentLayout = itemView.findViewById(R.id.parent_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);
            baseExperience = itemView.findViewById(R.id.base_experience_pokemon);
            moves = itemView.findViewById(R.id.move_pokemon);
            stats = itemView.findViewById(R.id.stat_pokemon);
            types = itemView.findViewById(R.id.type_pokemon);

        }
    }

    private void createTableOnFirstStart(){
        dbHelper.insertEmptyTable();
        SharedPreferences preferences = mContext.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("firstStart", true);
        editor.apply();
    }

    private void readCursorData(pokemon pokemonNow,ViewHolder viewHolder){
        long id = mCursor.getLong(mCursor.getColumnIndex(FavouritePokemonContract.FavouritePokemonEntry._ID));
        Cursor cursor =dbHelper.readData(id);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try{
            while(cursor.moveToNext()){
                String fav_status = mCursor.getString(mCursor.getColumnIndex(FavouritePokemonContract.FavouritePokemonEntry.FAV_STATUS));
                pokemonNow.setFav_status(fav_status);
                if (fav_status != null && fav_status.equals("1")){
                    viewHolder.addToFav.setBackgroundResource(R.drawable.ic_add_to_favourite);
                }else {
                    if (fav_status != null && fav_status.equals("0")){
                        viewHolder.addToFav.setBackgroundResource(R.drawable.ic_favourite_grey);
                    }
                }
            }
        }finally {

            if (cursor!=null&&cursor.isClosed()){
                cursor.close();
                db.close();
            }


        }

    }


}
