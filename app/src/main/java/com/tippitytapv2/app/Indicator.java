package com.tippitytapv2.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Wietse on 6/19/2014.
 */
public class Indicator extends SurfaceView
        implements SurfaceHolder.Callback{

    private SurfaceHolder holder;
    private Context context;
    private IndicatorThread thread;

    public Indicator(Context context, AttributeSet attrSet) {
        super(context, attrSet);
        holder = getHolder();
        holder.addCallback(this);
        this.context = context;
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

    }

    public void doDraw(Canvas canvas){
        canvas.drawColor(Color.GREEN);

    }
}