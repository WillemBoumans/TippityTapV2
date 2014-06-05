package com.tippitytapv2.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;

import android.view.View;



public class MainMenu extends Activity {

    protected static final int REQUEST_SONG = 1;
    protected static final String SONG_URI = "SONG_URI";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }




    public void playButton (View view)        {
        Intent selectSong = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(selectSong, REQUEST_SONG);
    }

    public void onActivityResult(int RequestCode, int resultCode, Intent intent){
        if(RequestCode == REQUEST_SONG){
            Intent toGame = new Intent(this, Game.class);
            Uri song = intent.getData();
            assert song != null;
            toGame.putExtra(SONG_URI,song.toString());
            startActivity(toGame);
        }
    }

    public void HighScores(View view) {
        Intent intent = new Intent(this, HighScores.class);
        startActivity(intent);
    }

    public void makeTipMap(View view) {
    }
}
