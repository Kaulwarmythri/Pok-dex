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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pokdex.types.Pokemon.Pokemon;
import com.example.pokdex.types.Pokemon.pokemonName;
import com.example.pokdex.types.types;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentType extends Fragment {
    private static final String TAG = "FragmentType";
    private RecyclerViewAdapterT adapter;
    RecyclerView mRecyclerView;
    private ArrayList<types> typeList;
    private String pokemonList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_type_layout, container, false);
        Log.d(TAG, "onCreateView: Infalting type");
        mRecyclerView = view.findViewById(R.id.recycler_view_types);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokeAPI api2 = retrofit.create(PokeAPI.class);

        typeList = new ArrayList<>();

        for (int i=1;i<=18;i++){
            Call<types> call = api2.getTypes(i);
            call.enqueue(new Callback<types>() {
                @Override
                public void onResponse(Call<types> call, Response<types> response){
                    if (!response.isSuccessful()){
                        Toast.makeText(getActivity(), "Unable to connect.", Toast.LENGTH_SHORT).show();
                    }
                    assert response.body() != null;
                    String temp = response.body().getName();
                    ArrayList<Pokemon> pokemonNamesList = response.body().getPokemonList();
                    typeList.add(new types(temp,pokemonNamesList));
                }

                @Override
                public void onFailure(Call<types> call, Throwable t) {
                    t.printStackTrace();

                }
            });
        }
        initRecyclerView();
        return view;

    }

    public void initRecyclerView(){
        adapter = new RecyclerViewAdapterT(getActivity(), typeList);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.example_menu, menu);
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
                return true;
            }
        });
    }
}
