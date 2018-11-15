package com.example.isen.playeraudio;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class Player extends AppCompatActivity {

    private static boolean playPause = false;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
    }

    public void onClickPlay(View v){
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this,R.raw.powerwolf);
            mediaPlayer.start();
            playPause = !playPause;
        }else {
            if(playPause){
                mediaPlayer.pause();
                playPause = !playPause;
            }
            else {
                mediaPlayer.start();
                playPause = !playPause;
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mediaPlayer != null){
            mediaPlayer.stop();;
        }
    }
}
e