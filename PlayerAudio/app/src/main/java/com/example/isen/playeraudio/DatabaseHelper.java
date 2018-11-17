package com.example.isen.playeraudio;

import android.arch.persistence.room.Room;

import java.util.ArrayList;


public class DatabaseHelper {

    static DatabaseHelper instance = null;
    private final SongDatabase db;
    public static DatabaseHelper getInstance(){
        if(instance == null){
            instance = new DatabaseHelper();
        }
        return instance;

    }
    public void fillDatabase(ArrayList<Song> songs){
        db.databaseDao().insertAll(songs);
    }

    public SongDatabase getDatabase(){
        return db;
    }
    public DatabaseDao getDatabaseDao(){
        return db.databaseDao();
    }

    public DatabaseHelper(){
        db = Room.databaseBuilder(ListActivity.getContext(),SongDatabase.class,"ma_bdd.db").build();
    }
}
