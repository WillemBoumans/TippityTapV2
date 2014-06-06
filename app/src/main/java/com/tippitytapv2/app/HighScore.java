package com.tippitytapv2.app;

import java.io.Serializable;

/**
 * Created by Wietse on 6/6/2014.
 */
public class HighScore implements Serializable {

    private String song_name;
    private int score;
    private int max_score;

    public HighScore(String song_name, int score, int max_score){
        this.max_score = max_score;
        this.score = score;
        this.song_name = song_name;
    }

    @Override
    public String toString(){
        return song_name + " : " + Integer.toString(score) + " / " + Integer.toString(max_score);
    }

    public int getScore(){
        return score;
    }

    public String getSong_name(){
        return song_name;
    }

}
