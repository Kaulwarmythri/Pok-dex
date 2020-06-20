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

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentStat extends Fragment {
    private static final String TAG = "FragmentStat";
    private RecyclerViewAdapterS adapter;
    private ArrayList<stats> statList;
    private RecyclerView mRecyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stat_layout, container, false);
        Log.d(TAG, "onCreateView: Inflating stat");
        mRecyclerView = view.findViewById(R.id.recycler_view_stat);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokeAPI api4 = retrofit.create(PokeAPI.class);
        statList = new ArrayList<>();
        for (int i=1;i<=8;i++){
            Call<stats> call = api4.getStat(i);
            call.enqueue(new Callback<stats>() {
                @Override
                public void onResponse(Call<stats> call, Response<stats> response) {
                    if (!response.isSuccessful()){
                        Toast.makeText(getActivity(), "Unable to connect", Toast.LENGTH_SHORT).show();
                    }
                    String temp = response.body().getStat();
                    statList.add(new stats(temp));
                }

                @Override
                public void onFailure(Call<stats> call, Throwable t) {
                    t.printStackTrace();

                }
            });
        }

        initRecyclerView();
        return  view;
    }

    private void initRecyclerView(){
        adapter = new RecyclerViewAdapterS(getActivity(), statList);
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
                return false;
            }
        });
    }

}
