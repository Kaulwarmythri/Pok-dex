package com.example.pokdex;

import com.example.pokdex.evolutionChain.evolutionChain;
import com.example.pokdex.pokemon.pokemon;
import com.example.pokdex.types.types;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface PokeAPI {

 public final String BASE_URL = " https://pokeapi.co/api/v2/";

 @GET("pokemon/{id}")
 Call<pokemon> getForms(@Path("id") int userId);

 @GET("type/{id}")
 Call<types> getTypes(@Path("id") int userId);

 @GET("item/{id}")
 Call<items> getItems(@Path("id") int userId);

 @GET("stat/{id}")
 Call<stats> getStat(@Path("id") int userId);

 @GET("evolution-chain/{id}")
 Call<evolutionChain> getEvolutionChain(@Path("id") int userId);


}
