package com.example.isen.playeraudio;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class SongAdapter extends BaseAdapter {
    private ArrayList<Song> musics;
    private LayoutInflater musicInf;

    public SongAdapter(Context c, ArrayList<Song> songList){
        musics = songList;
        musicInf = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return musics.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //map to music layout
        LinearLayout musicLay = (LinearLayout) musicInf.inflate(R.layout.activity_list_song, parent, false);
        //get title and artist view
        TextView titleView = musicLay.findViewById(R.id.song_title);
        TextView artistView = musicLay.findViewById(R.id.song_artist);
        //get song using position
        Song currMusic = musics.get(position);
        titleView.setText(currMusic.getTitle());
        artistView.setText(currMusic.getArtist());
        //musicLay.setTag(position);
        return musicLay;
    }


}
