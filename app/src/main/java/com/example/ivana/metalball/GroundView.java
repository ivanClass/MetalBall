package com.example.ivana.metalball;

import android.app.Service;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Vibrator;
import android.view.Display;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by ivana on 05/03/2017.
 */

public class GroundView extends SurfaceView implements SurfaceHolder.Callback {
    private float cx = 10;
    private float cy = 10;

    private float lastGX = 0;
    private float lastGY = 0;

    private int pictureHeight = 0;
    private int pictureWidth = 0;
    private Bitmap icon = null;

    private int width = 0;
    private int height = 0;

    private boolean noBorderX = false;
    private boolean noBorderY = false;

    private Vibrator vibratorService = null;
    private DrawThread thread;

    public GroundView(Context context){
        super(context);

        getHolder().addCallback(this);

        thread = new DrawThread(getHolder(), this);
        Display display = ((WindowManager)context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        width = display.getWidth();
        height = display.getHeight();

        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ball);

        pictureHeight = icon.getHeight();
        pictureWidth = icon.getWidth();

        vibratorService = (Vibrator)(context.getSystemService(Service.VIBRATOR_SERVICE));
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    protected void onDraw(Canvas canvas){
        if(canvas != null){
            canvas.drawColor(0xFFAAAAAA);
            canvas.drawBitmap(icon, cx, cy, null);
        }
    }

    public void updateMe(float inx, float iny){
        lastGX += inx;
        lastGY += iny;

        cx += (lastGX);
        cy += (lastGY);

        if(cx > (width - pictureWidth)){
            cx = width - pictureWidth;
            lastGX = 0;
            if(noBorderX){
                vibratorService.vibrate(100);
                noBorderX = false;
            }
        }
        else{
            if(cx < (0)){
                cx = 0;
                lastGX = 0;

                if(noBorderX){
                    vibratorService.vibrate(100);
                    noBorderX = false;
                }
            }
            else{
                noBorderX = true;
            }
        }

        if(cy > (height - pictureHeight)){
            cy = height - pictureHeight;
            lastGY = 0;
            if(noBorderY){
                vibratorService.vibrate(100);
                noBorderY = false;
            }
        }
        else{
            if(cy < (0)){
                cy = 0;
                lastGY = 0;

                if(noBorderY){
                    vibratorService.vibrate(100);
                    noBorderY = false;
                }
            }
            else{
                noBorderY = true;
            }
        }

        invalidate();
    }
}
