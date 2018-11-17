package com.example.isen.playeraudio;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DatabaseDao {
    @Query("SELECT * FROM Song")
    List<Song> getAll();

    @Insert
    void insertAll(Song... songList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(ArrayList<Song> songs);
    @Delete
    void delete(Song tweet);

}