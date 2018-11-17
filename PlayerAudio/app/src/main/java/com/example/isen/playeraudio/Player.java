package com.example.isen.playeraudio;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class Player extends AppCompatActivity {

    private static boolean playPause = false;
    private static MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        if(mediaPlayer == null){
            mediaPlayer = MediaPlayer.create(this,R.raw.powerwolf);
            TextView timeEnd = findViewById(R.id.timeEnd);
            timeEnd.setText(msToString(mediaPlayer.getDuration()));
        }
        if(playPause){
            findViewById(R.id.playPause).setBackgroundResource(R.drawable.pausebutton);
        }
    }

    private String msToString(int ms){
        int s = ms/1000;
        int m = s/60;
        s = s - 60*m;
        return String.valueOf(m)+":"+String.valueOf(s);
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

    @Override
    protected void onStop() {
        super.onStop();
        /*if(mediaPlayer != null){
            mediaPlayer.stop();
        }*/
    }
}
