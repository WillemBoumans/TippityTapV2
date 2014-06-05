package com.tippitytapv2.app;

import android.app.Activity;
import android.app.DialogFragment;
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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Date;
import java.util.LinkedList;


public class Game extends Activity
        implements NewTipMapDialogFragment.NewTipMapDialogListener, TipMapDialogFragment.TipMapDialogListener,
        MediaPlayer.OnCompletionListener{

    private MediaPlayer m;
    private TipMap tipMap;
    private long start;
    private LinkedList<Long> player_taps;
    private int score = 0;
    private int max_score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Prepare the audio playback
        AudioManager AM = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        AM.setStreamMute(AudioManager.STREAM_MUSIC, false);
        setContentView(R.layout.activity_game);
        Intent intent = getIntent();
        String song_uri = intent.getStringExtra(MainMenu.SONG_URI);
        Uri Song;
        Song = Uri.parse(song_uri);
        TextView song_name_view = (TextView)findViewById(R.id.song_playing);
        MediaMetadataRetriever meta = new MediaMetadataRetriever();
        meta.setDataSource(getApplicationContext(),Song);
        String song_name = meta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        song_name_view.setText(song_name);
        //Try to load TipMap, otherwise create one.
        try {
            FileInputStream fis = getApplicationContext().openFileInput(song_name);
            ObjectInputStream is = new ObjectInputStream(fis);
            tipMap = (TipMap) is.readObject();
            is.close();
            fis.close();
        } catch (FileNotFoundException e) {
            tipMap = new TipMap(song_name);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        m = new MediaPlayer();
        m.setAudioStreamType(AudioManager.STREAM_MUSIC);
        m.setOnCompletionListener(this);
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
        if(tipMap.isBeing_created()) {
            DialogFragment dialog = new NewTipMapDialogFragment();
            dialog.show(getFragmentManager(), "NewTipMapDialogFragment");
        }
        else{
            player_taps = new LinkedList<Long>();
            max_score = tipMap.max_score();
            DialogFragment dialog = new TipMapDialogFragment();
            dialog.show(getFragmentManager(), "TipMapDialogFragment");
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        if( m != null){
            m.release();
            finish();
        }
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


    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        //Game is started
        Button button = (Button)findViewById(R.id.Tap);
        m.start();
        button.setEnabled(true);
        start = System.currentTimeMillis();



    }

    public void Tap(View view) {
        if (tipMap.isBeing_created()) {
            tipMap.addTip(System.currentTimeMillis() - start);
        }
        else {
            player_taps.add(System.currentTimeMillis() - start);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        if(tipMap.isBeing_created()) {
            tipMap.finished();
            try {
                FileOutputStream fos = getApplicationContext().openFileOutput(tipMap.getSong_name(), Context.MODE_PRIVATE);
                ObjectOutputStream os = new ObjectOutputStream(fos);
                os.writeObject(tipMap);
                os.close();
                fos.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            for(long l : player_taps){
                if(tipMap.contains_tip(l)){
                    score+= 2;
                }
                else{
                    score--;
                }
            }
            Button Tap = (Button)findViewById(R.id.Tap);
            Tap.setVisibility(View.INVISIBLE);
            TextView score_text = (TextView)findViewById(R.id.GameScoreTextView);
            score_text.setText(score + " out of " + max_score);
            score_text.setVisibility(View.VISIBLE);
        }

    }
}
