package com.example.isen.playeraudio.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.isen.playeraudio.DatabaseHelper;
import com.example.isen.playeraudio.Song;

import java.util.ArrayList;
import java.util.List;

public class FillDatabase extends AsyncTask<ArrayList<Song>,Void,Void> {


    @Override
    protected Void doInBackground(ArrayList<Song>... arrayLists) {
        DatabaseHelper.getInstance().getDatabaseDao().insertAll(arrayLists[0]);



        return null;
    }
}
