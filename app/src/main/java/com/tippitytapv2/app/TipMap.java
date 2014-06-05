package com.tippitytapv2.app;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Wietse on 6/5/2014.
 */
public class TipMap implements Serializable{

    private String song_name;
    private LinkedList<Long> taps;
    private boolean being_created;

    public TipMap(String song_name){
        this.song_name = song_name;
        taps = new LinkedList<Long>();
        being_created = true;

    }

    public boolean contains_tip(long tip){
        for(long tap : taps){
            if( tap > tip - 50 && tap < tip + 50 ){
                return true;
            }

        }
        return false;
    }

    public int max_score(){
        return taps.size();
    }

    public boolean isBeing_created(){
        return being_created;
    }

    public void addTip(Long time){
        taps.add(time);
    }

    public LinkedList<Long> getTaps(){
        return taps;
    }

    public void finished(){
        being_created = false;
    }

    public String getSong_name(){
        return song_name;
    }
}
