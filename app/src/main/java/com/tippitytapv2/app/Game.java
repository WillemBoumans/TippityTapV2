package com.tippitytapv2.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.IOException;



public class Game extends Activity {

    private MediaPlayer m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AudioManager AM = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        AM.setStreamMute(AudioManager.STREAM_MUSIC, false);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        String song_uri = intent.getStringExtra(MainMenu.SONG_URI);

        Uri Song;
        Song = Uri.parse(song_uri);
        m = new MediaPlayer();
        m.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            m.setDataSource(getApplicationContext(), Song);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            m.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        m.start();
        TextView song_name_view = (TextView)findViewById(R.id.song_playing);
        MediaMetadataRetriever meta = new MediaMetadataRetriever();
        meta.setDataSource(getApplicationContext(),Song);
        String song_name = meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        song_name_view.setText(song_name);
    }

    @Override
    protected void onPause() {
        super.onPause();
        m.release();
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
