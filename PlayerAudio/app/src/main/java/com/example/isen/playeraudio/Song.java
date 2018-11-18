package com.example.isen.playeraudio;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Song {


    @NonNull @PrimaryKey
    private long id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "artist")
    private String artist;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }


    public Song(long id, String title, String artist) {
        this.id = id;
        this.title=title;
        this.artist=artist;
    }

    public long getID(){return id;}
    public String getTitle(){return title;}
    public String getArtist(){return artist;}
}
