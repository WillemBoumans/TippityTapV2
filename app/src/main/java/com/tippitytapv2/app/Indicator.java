package com.tippitytapv2.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

/**
 * Created by Wietse on 6/19/2014.
 */
public class Indicator extends SurfaceView
        implements SurfaceHolder.Callback{

    private SurfaceHolder holder;
    private Context context;
    private IndicatorThread thread;
    private TipMap tipMap;
    private long start;

    public Indicator(Context context, AttributeSet attrSet) {
        super(context, attrSet);
        holder = getHolder();
        holder.addCallback(this);
        this.context = context;
    }

    public void setTipMap(TipMap tipMap){
        this.tipMap = tipMap;
    }

    public void start(long start){
        this.start = start;
        thread.setRunning(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

        thread = new IndicatorThread(holder, context, this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        thread.setRunning(false);
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void doDraw(Canvas canvas){
        if(tipMap != null) {
            if (tipMap.isBeing_created()) {

                setVisibility(View.INVISIBLE);
                thread.setRunning(false);
            }
            else {
                if (tipMap.contains_tip(System.currentTimeMillis() - start + 100)) {
                    canvas.drawColor(Color.GREEN);
                } else {
                    canvas.drawColor(Color.RED);
                }
            }
        }
    }
}