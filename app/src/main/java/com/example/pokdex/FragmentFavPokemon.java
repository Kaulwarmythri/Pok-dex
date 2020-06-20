package com.example.pokdex;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.pokdex.FavouritePokemonContract.*;
import com.example.pokdex.pokemon.pokemon;

import java.util.ArrayList;

public class FragmentFavPokemon extends FragmentPokemon {
    private static final String TAG = "FragmentFavPokemon";
    private RecyclerView mRecyclerView;
    private FavouritePokemonDbHelper mDbHelper;
    private ArrayList<FavPokemon> mFavPokemonList;
    private ArrayList<pokemon> mPokemonList;
    private SQLiteDatabase dataBase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_layout, container, false);
        Log.d(TAG, "onCreateView: Starting");
        mRecyclerView = view.findViewById(R.id.favourite_pokemon_recycler_view);
        mFavPokemonList = new ArrayList<>();
        mPokemonList = new ArrayList<>();
        mDbHelper = new FavouritePokemonDbHelper(getActivity());
        FavouritePokemonDbHelper dbHelper = new FavouritePokemonDbHelper(getActivity());
        dataBase = dbHelper.getWritableDatabase();
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback
                (0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                removeItem((long) viewHolder.itemView.getTag());

            }
        }).attachToRecyclerView(mRecyclerView);
        loadData();
        initRecyclerView();
        return view;
    }

    private void removeItem(long id) {
        dataBase.delete(FavouritePokemonContract.FavouritePokemonEntry.TABLE_NAME,
                FavouritePokemonContract.FavouritePokemonEntry._ID + "=" + id, null);
    }



    public void initRecyclerView(){
        FavPokemonAdapter adapter = new FavPokemonAdapter(getActivity(), mFavPokemonList, mPokemonList);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void loadData(){
        if (mFavPokemonList != null){
            mFavPokemonList.clear();
        }
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = mDbHelper.selectAll();
        try {
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(FavouritePokemonEntry.COLUMN_NAME));
                String id = cursor.getString(cursor.getColumnIndex(FavouritePokemonEntry._ID));
                String imageURL = cursor.getString(cursor.getColumnIndex(FavouritePokemonEntry.COLUMN_IMAGE));
                String baseExperience = cursor.getString(cursor.getColumnIndex(FavouritePokemonEntry.COLUMN_BASE_EXPERIENCE));
                String type = cursor.getString(cursor.getColumnIndex(FavouritePokemonEntry.COLUMN_TYPE));
                String move = cursor.getString(cursor.getColumnIndex(FavouritePokemonEntry.COLUMN_MOVE));
                String stat = cursor.getString(cursor.getColumnIndex(FavouritePokemonEntry.COLUMN_STAT));
                FavPokemon favPokemon = new FavPokemon(id,name,imageURL,baseExperience,type,move,stat);
                mFavPokemonList.add(favPokemon);
            }
        }finally {
            if (cursor != null && cursor.isClosed()){
                cursor.close();
                db.close();
            }
        }
    }
}
