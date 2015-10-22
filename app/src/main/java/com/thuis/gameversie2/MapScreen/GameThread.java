package com.thuis.gameversie2.MapScreen;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Elize on 1-8-2015.
 */
public class GameThread extends Thread {

    private SurfaceHolder surfaceHolder = null;
    private MapSurfaceView mapSurfaceView = null;
    private boolean running = false;
    private Canvas canvas = null;

    public static int getMaxFps() {
        return MAX_FPS;
    }

    private final static int MAX_FPS = 30;

    private final static int MAX_FRAME_SKIPS = 3;

    private final static int FRAME_PERIOD = 1000 / MAX_FPS;

    public GameThread(SurfaceHolder surfaceHolder, MapSurfaceView mapSurfaceView ){
        this.surfaceHolder = surfaceHolder;
        this.mapSurfaceView = mapSurfaceView;

    }

    @Override
    public void run() {

        long startTime = 0;
        long timeDifference = 0;
        int sleepTime = 0 ;
        int framesSkipped = 0;

        while(running) {

            canvas = null;

            try {
                canvas = surfaceHolder.lockCanvas();

                synchronized(surfaceHolder) {

                    startTime = System.currentTimeMillis();
                    framesSkipped = 0;

                    mapSurfaceView.update();
                    mapSurfaceView.drawScreen(canvas);

                    timeDifference = System.currentTimeMillis() - startTime;
                    sleepTime = (int)(FRAME_PERIOD - timeDifference);

                    if (sleepTime > 0) {
                        try {
                            Thread.sleep(sleepTime);
                        } catch (InterruptedException e) {}
                    }

                    while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {

                        mapSurfaceView.update();
                        sleepTime += FRAME_PERIOD;
                        framesSkipped++;
                    }
                }

            }catch(Exception exception){
                exception.printStackTrace();
            }finally {
                if(canvas != null) {
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }

            }

        }

    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void startThread() {
        if(!isAlive()) {
            start();
        }
    }
}
