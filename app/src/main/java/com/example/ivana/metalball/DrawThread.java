package com.example.ivana.metalball;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by ivana on 05/03/2017.
 */

public class DrawThread extends Thread {
    private SurfaceHolder surfaceHolder;
    private GroundView panel;
    private boolean run = false;

    public DrawThread(SurfaceHolder surfaceHolder, GroundView panel){
        this.surfaceHolder = surfaceHolder;
        this.panel = panel;
    }

    public void setRunning(boolean run){
        this.run = run;
    }

    public void run(){
        Canvas c;

        while (run){
            c = null;

            try {
                c = surfaceHolder.lockCanvas(null);
                synchronized (surfaceHolder){
                    panel.onDraw(c);
                }
            } finally {
                if(c != null){
                    surfaceHolder.unlockCanvasAndPost(c);
                }
            }
        }
    }
}
