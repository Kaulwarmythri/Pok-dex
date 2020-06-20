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

import com.example.pokdex.evolutionChain.Chain.Chain;
import com.example.pokdex.evolutionChain.Chain.Species3;
import com.example.pokdex.evolutionChain.evolutionChain;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentEvolution extends Fragment {
    private static final String TAG = "FragmentEvolution";
    public final String BASE_URL =  "https://pokeapi.co/api/v2/";
    private ArrayList<evolutionChain> chainList;
    private  RecyclerViewAdapterE adapter;
    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_evolution_layout, container, false);
        Log.d(TAG, "onCreateView: Inflating evolution chain");
        recyclerView = view.findViewById(R.id.recyler_view_evolution);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PokeAPI api5 = retrofit.create(PokeAPI.class);
        chainList = new ArrayList<>();

        for (int i=1; i<=50;i++){
        Call<evolutionChain> call = api5.getEvolutionChain(i);
        call.enqueue(new Callback<evolutionChain>() {
            @Override
            public void onResponse(Call<evolutionChain> call, Response<evolutionChain> response) {
                if (!response.isSuccessful()){
                Toast.makeText(getActivity(),"Couldn't connect",Toast.LENGTH_SHORT).show();
                }
                evolutionChain chain = response.body();
                chainList.add(chain);
            }
            @Override
            public void onFailure(Call<evolutionChain> call, Throwable t) {
                t.printStackTrace();

            }
        });
        }
        initRecyclerView();
        return  view;
    }

    public void initRecyclerView(){
        adapter = new RecyclerViewAdapterE(getActivity(), chainList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
                return false;
            }
        });
    }
}
