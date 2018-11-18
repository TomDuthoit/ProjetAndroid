package com.example.isen.playeraudio.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.isen.playeraudio.DatabaseHelper;
import com.example.isen.playeraudio.Song;
import com.example.isen.playeraudio.SongGetter;

import java.util.List;

public class PullDatabase extends AsyncTask<Integer,Void,List<Song>> {
    SongGetter mlistener;

    @Override
    protected List<Song> doInBackground(Integer ... id) {
        List<Song> test = DatabaseHelper.getInstance().getDatabaseDao().getAll();


        return test;
    }
    public PullDatabase(SongGetter mlistener) {
        this.mlistener = mlistener;
    }

    @Override
    protected void onPostExecute(List<Song> songs){
        super.onPostExecute(songs);
        mlistener.onSongRetrived(songs);
    }
}
