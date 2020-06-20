package com.example.pokdex;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.pokdex.FavouritePokemonContract.*;

import androidx.annotation.Nullable;

public class FavouritePokemonDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "favouritePokemon.db";
    public static final int DATABASE_VERSION = 1;
    public FavouritePokemonDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVOURITE_POKEMON_TABLE = "CREATE TABLE " +
                FavouritePokemonEntry.TABLE_NAME + "( " +
                FavouritePokemonEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                FavouritePokemonEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                FavouritePokemonEntry.COLUMN_IMAGE + " TEXT NOT NULL, " +
                FavouritePokemonEntry.COLUMN_TYPE + " TEXT NOT NULL, " +
                FavouritePokemonEntry.COLUMN_STAT + " TEXT NOT NULL, " +
                FavouritePokemonEntry.COLUMN_MOVE + " TEXT NOT NULL, " +
                FavouritePokemonEntry.FAV_STATUS + " TEXT NOT NULL, " +
                FavouritePokemonEntry.COLUMN_TIMESTAMP + "TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";

        db.execSQL(SQL_CREATE_FAVOURITE_POKEMON_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + FavouritePokemonEntry.TABLE_NAME);
        onCreate(db);
    }

    public void insertEmptyTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FavouritePokemonEntry.FAV_STATUS, "0");
        db.insert(FavouritePokemonEntry.TABLE_NAME,null,cv);
    }
    public void insertValues(String name,String type,String Stat,String moves,
                             String baseExperience,String imageName,String fav_status){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(FavouritePokemonEntry.COLUMN_IMAGE,imageName);
        cv.put(FavouritePokemonEntry.COLUMN_NAME,name);
        cv.put(FavouritePokemonEntry.COLUMN_BASE_EXPERIENCE,baseExperience);
        cv.put(FavouritePokemonEntry.COLUMN_TYPE,type);
        cv.put(FavouritePokemonEntry.COLUMN_MOVE,moves);
        cv.put(FavouritePokemonEntry.COLUMN_STAT,Stat);
        db.insert(FavouritePokemonEntry.TABLE_NAME,null,cv);

    }
    public Cursor readData(Long id){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + FavouritePokemonEntry.TABLE_NAME + "where " + FavouritePokemonEntry._ID + "="+id+"";
        return db.rawQuery(sql,null,null);

    }

    public void removeData(Long id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE " + FavouritePokemonEntry.TABLE_NAME + " SET "
                + FavouritePokemonEntry.FAV_STATUS + " ='0' WHERE " + FavouritePokemonEntry._ID + "=" + id + "";
        db.execSQL(sql);
    }
    public Cursor selectAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + FavouritePokemonEntry.TABLE_NAME + " WHERE " + FavouritePokemonEntry.FAV_STATUS + "='1'";
        return db.rawQuery(sql,null,null);
    }
}
