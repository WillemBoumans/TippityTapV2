package com.tippitytapv2.app;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;


public class HighScores extends Activity{

    SimpleCursorAdapter mAdapter;
    protected static final String HIGH_SCORES_FILENAME = "high_scores";
    private ArrayList<String> HighScoreStrings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_scores);
        HighScoreStrings = new ArrayList<String>();
        HighScoresContainer HSC = null;
        try {
            FileInputStream fis = getApplicationContext().openFileInput(HighScores.HIGH_SCORES_FILENAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            HSC = (HighScoresContainer) is.readObject();
            is.close();
            fis.close();
        } catch (FileNotFoundException e) {
            HSC = new HighScoresContainer();
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        for(HighScore HS : HSC.HighScores){
            HighScoreStrings.add(HS.toString());
        }
        ListView lv = (ListView) findViewById(R.id.High_scores_ListView);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                HighScoreStrings);
        lv.setAdapter(arrayAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.high_scores, menu);
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
