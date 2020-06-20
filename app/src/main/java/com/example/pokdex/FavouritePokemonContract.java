package com.example.pokdex;

import android.provider.BaseColumns;

public class FavouritePokemonContract {

    public FavouritePokemonContract(){}

    public static  final class FavouritePokemonEntry implements BaseColumns {

        public static final String TABLE_NAME = "FavouritePokemonList";
        public static final String COLUMN_NAME = "PokemonName";
        public static final String COLUMN_IMAGE = "PokemonImage";
        public static final String COLUMN_BASE_EXPERIENCE = "PokemonBaseExperience";
        public static final String COLUMN_TYPE = "PokemonType";
        public static final String COLUMN_STAT = "PokemonStat";
        public static final String COLUMN_MOVE = "PokemonMove";
        public static final String COLUMN_TIMESTAMP = "TimeStamp";
        public static String FAV_STATUS = "fStatus";


    }
}
