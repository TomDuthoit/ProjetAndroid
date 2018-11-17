package com.example.isen.playeraudio;

import android.Manifest;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import com.example.isen.playeraudio.SongService.MusicBinder;

public class ListActivity extends BaseActivity{
    private ArrayList<Song> songList;
    private ListView songView;
    //private SongService musicSrv;
    //private Intent playIntent;
    private boolean musicBound=false;
    private static int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE;
    private static Song songPlaying;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        while (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
            //return;
        }

        songView = findViewById(R.id.song_list);
        songList = new ArrayList<>();
        getSongList();
        Collections.sort(songList, new Comparator<Song>(){
            public int compare(Song a, Song b){
                return a.getTitle().compareTo(b.getTitle());
            }
        });

        SongAdapter musicAdt = new SongAdapter(this, songList);
        songView.setAdapter(musicAdt);
    }

    @Override
    protected void onStart() {
        super.onStart();
        /*if(playIntent==null){
            playIntent = new Intent(this, SongService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }*/
    }

    public void songPicked(View view){
        currSong = songList.get(Integer.parseInt(view.getTag().toString()));

        Uri trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSong.getID());
        if(null!= mediaPlayer && songPlaying!=currSong){
            mediaPlayer.stop();
            playPause = false;
            mediaPlayer = MediaPlayer.create(this,trackUri);
            songPlaying = currSong;
        }
        if(null== mediaPlayer && songPlaying== null){
            mediaPlayer = MediaPlayer.create(this,trackUri);
            songPlaying = currSong;
        }
        Intent intent = new Intent(this,Player.class);
        startActivity(intent);
    }

    //connect to the service
    /*private ServiceConnection musicConnection = new ServiceConnection(){

        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicBinder binder = (MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(songList);
            musicBound = true;
        }

        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };*/

    public void getSongList() {
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0";
        String sortOrder = MediaStore.Audio.Media.TITLE + " ASC";
        Cursor musicCursor = musicResolver.query(musicUri, null, selection, null, sortOrder);
        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                songList.add(new Song(thisId, thisTitle, thisArtist));
            } while (musicCursor.moveToNext());
        }
    }

    @Override
    protected void onDestroy() {
        /*stopService(playIntent);
        musicSrv=null;
        */super.onDestroy();
    }
}
