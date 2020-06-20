package com.example.pokdex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: Starting");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new FragmentPokemon()).commit();
            navigationView.setCheckedItem(R.id.pokémon);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.pokémon:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, new FragmentPokemon()).commit();
                break;
            case R.id.types:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, new FragmentType()).commit();
                break;
            case R.id.items:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, new FragmentItem()).commit();
                break;
            case R.id.evolution_chain:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, new FragmentEvolution()).commit();
                break;
            case R.id.stats:
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container, new FragmentStat()).commit();
                break;
            case R.id.favourite:
                Toast.makeText(this, "Add to favourite", Toast.LENGTH_SHORT).show();
                getSupportFragmentManager().beginTransaction().
                        replace(R.id.fragment_container,new FragmentFavPokemon()).commit();
                break;

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

}
