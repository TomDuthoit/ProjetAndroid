package com.example.isen.playeraudio;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;




@Database(entities = {Song.class}, version = 1)

public abstract class SongDatabase extends RoomDatabase {

    public abstract DatabaseDao databaseDao();

}