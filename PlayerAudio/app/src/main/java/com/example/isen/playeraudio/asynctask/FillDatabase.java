package com.example.isen.playeraudio.asynctask;

import android.os.AsyncTask;

import com.example.isen.playeraudio.DatabaseHelper;
import com.example.isen.playeraudio.Song;

import java.util.ArrayList;

public class FillDatabase extends AsyncTask<ArrayList<Song>,Void,Void> {


    @Override
    protected Void doInBackground(ArrayList<Song>... arrayLists) {
        DatabaseHelper.getInstance().fillDatabase(arrayLists[0]);
        return null;
    }
}
