package com.example.pokdex;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokdex.pokemon.moves.moves;
import com.example.pokdex.pokemon.pokemon;
import com.example.pokdex.pokemon.stats.stats;
import com.example.pokdex.pokemon.types.types;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentPokemon extends Fragment {
    private static final String TAG = "FragmentPokemon";
    private RecyclerViewAdapterP adapter;
    private RecyclerView mRecyclerView;
    private ArrayList<pokemon> mPokemonList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pokemon_layout, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view_pokemon);
        Log.d(TAG, "onCreateView: Starting");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokeAPI api = retrofit.create(PokeAPI.class);
        mPokemonList = new ArrayList<>();
        for (int i = 1; i <= 50; i++){
            Call<pokemon> call = api.getForms(i);
            String imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + i + ".png";
            call.enqueue(new Callback<pokemon>() {
                @Override
                public void onResponse(Call<pokemon> call, Response<pokemon> response) {

                    assert response.body() != null;
                    String name = response.body().getName();
                    ArrayList<types> typeNamesList = response.body().getTypesList();
                    ArrayList<stats> statNameList = response.body().getStatList();
                    ArrayList<moves> moveNameList = response.body().getMovesList();
                    mPokemonList.add(new pokemon(name,imageUrl,moveNameList,typeNamesList,statNameList));
                }
                @Override
                public void onFailure(Call<pokemon> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
        initRecyclerView();
        return view;
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: Preparing the recyclerView");
        adapter = new RecyclerViewAdapterP(getActivity(),mPokemonList);
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.example_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.search_badge);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }


}
