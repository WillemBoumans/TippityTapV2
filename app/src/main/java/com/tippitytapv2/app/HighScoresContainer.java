package com.tippitytapv2.app;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Wietse on 6/5/2014.
 */
public class HighScoresContainer implements Serializable {

    public ArrayList<HighScore> HighScores;

    public HighScoresContainer(){
        HighScores = new ArrayList<HighScore>();
    }



}
