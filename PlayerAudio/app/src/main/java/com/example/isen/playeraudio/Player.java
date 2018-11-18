package com.example.isen.playeraudio;

import android.content.ContentUris;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.isen.playeraudio.asynctask.PullDatabase;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;


public class Player extends BaseActivity  implements SongGetter {
    protected Thread thread;
    protected SeekBar progressBar;
    private List<Song> listSong;
    private int songIndex;
    public static boolean random = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        PullDatabase pullDatabase = new PullDatabase(this);
        pullDatabase.execute(0);

        mediaPlayer.start();
        playPause = true;

        progressBar = findViewById(R.id.progressBar);
        progressBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 0;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(progressChangedValue);
            }
        });

        thread = new Thread() {

            @Override
            public void run() {
                try {
                    while (!thread.isInterrupted()) {
                        Thread.sleep(100);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView timeEnd = findViewById(R.id.timeEnd);
                                TextView timeNow = findViewById(R.id.timeNow);
                                timeEnd.setText(msToString(mediaPlayer.getDuration()));
                                timeNow.setText(msToString(mediaPlayer.getCurrentPosition()));
                                progressBar.setProgress(0);
                                progressBar.setMax(mediaPlayer.getDuration());
                                progressBar.setProgress(mediaPlayer.getCurrentPosition());
                                TextView title = findViewById(R.id.player_title);
                                TextView artist = findViewById(R.id.player_artist);
                                title.setText(currSong.getTitle());
                                artist.setText(currSong.getArtist());
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };

        thread.start();
        if(playPause){
            findViewById(R.id.playPause).setBackgroundResource(R.drawable.pausebutton);
        }
    }

    private String msToString(int ms){
        int s = ms/1000;
        int m = s/60;
        String sec = String.valueOf(s - 60*m);
        if(sec.length()== 1){sec = "0"+sec;}
        String min = String.valueOf(m);
        if(min.length()== 1){min = "0"+min;}
        return min+":"+sec;
    }

    public void onClickPlay(View v){
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this,R.raw.powerwolf);
            mediaPlayer.start();
            v.findViewById(R.id.playPause).setBackgroundResource(R.drawable.pausebutton);
            playPause = !playPause;
        }else {
            if(playPause){
                mediaPlayer.pause();
                v.findViewById(R.id.playPause).setBackgroundResource(R.drawable.playbutton);
                playPause = !playPause;
            }
            else {
                mediaPlayer.start();
                v.findViewById(R.id.playPause).setBackgroundResource(R.drawable.pausebutton);
                playPause = !playPause;
            }
        }
    }

    public void onClickNext(View v){
        mediaPlayer.stop();
        songIndex = findSongInList();
        int adding = 1;
        if(random){
            Random r = new Random();
            adding = r.nextInt(listSong.size()-1) +1;
        }
        if(songIndex+adding >= listSong.size()){
            currSong = listSong.get(songIndex+adding-listSong.size());
        }
        else {
            currSong = listSong.get(songIndex+adding);
        }
        Uri trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSong.getID());
        mediaPlayer = MediaPlayer.create(this,trackUri);
        mediaPlayer.start();
    }

    public void onClickPrev(View v){
        mediaPlayer.stop();
        songIndex = findSongInList();
        int subbing = 1;
        if(random){
            Random r = new Random();
            subbing = r.nextInt(listSong.size()-1) +1;
        }
        if(songIndex - subbing < 0){
            currSong = listSong.get(listSong.size()+songIndex-subbing);
        }
        else {
            currSong = listSong.get(songIndex-subbing);
        }
        Uri trackUri = ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                currSong.getID());
        mediaPlayer = MediaPlayer.create(this,trackUri);
        mediaPlayer.start();
    }

    private Integer findSongInList(){
        for(int i = 0; i<listSong.size();i++){
            if(currSong.getID() == listSong.get(i).getID()){
                return i;
            }
        }
        return 0;
    }

    public void onClickRandom(View v){
        if(random){
            random = false;
            v.findViewById(R.id.player_random).setBackgroundResource(R.drawable.random);
        }
        else {
            random = true;
            v.findViewById(R.id.player_random).setBackgroundResource(R.drawable.randomized);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*if(mediaPlayer != null){
            mediaPlayer.stop();
        }*/
    }
    @Override
    public void onSongRetrieved(List<Song> songs) {
        listSong = songs;
        Collections.sort(listSong, new Comparator<Song>(){
            public int compare(Song a, Song b){
                return a.getTitle().compareTo(b.getTitle());
            }
        });
    }
}

