package com.example.isen.playeraudio;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class Player extends BaseActivity {
    protected Thread thread;
    protected SeekBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
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

    @Override
    protected void onStop() {
        super.onStop();
        /*if(mediaPlayer != null){
            mediaPlayer.stop();
        }*/
    }
}

