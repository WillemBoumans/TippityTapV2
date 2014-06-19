package com.tippitytapv2.app;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Wietse on 6/19/2014.
 */
public class IndicatorThread extends Thread {

    private boolean running;
    private SurfaceHolder surfaceHolder;
    private Context context;
    private Indicator indicator;
    private TipMap tipMap;

    public IndicatorThread(SurfaceHolder surfaceHolder, Context context, Indicator indicator){
        this. surfaceHolder = surfaceHolder;
        this.context = context;
        this. indicator = indicator;
        running = false;

    }

    public void setRunning (boolean run){
        running = run;
    }

    @Override
    public void run() {
        while(running){
            Canvas canvas = surfaceHolder.lockCanvas();
            if(canvas != null){
                indicator.doDraw(canvas);
                surfaceHolder.unlockCanvasAndPost(canvas);
            }
        }
    }
}
