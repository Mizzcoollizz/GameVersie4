package com.thuis.gameversie2.Character;

import android.graphics.Bitmap;
import android.util.Log;

import com.thuis.gameversie2.GamePanel;

/**
 * Created by Elize on 6-8-2015.
 */
public class CharacterAnimation {

    private Bitmap[][] frames = null;
    private int currentFrame = 0;
    private long startTime = 0;
    private long delay = 0;
    private boolean playedOnce = false ;
    private int currentDirection = 0;

    public void setFrames(Bitmap[][] frames){

        this.frames = frames;
        currentFrame = 0;
        startTime = System.currentTimeMillis();

    }
    public void setDelay(long delay){
        this.delay = delay;
    }

    public void setFrame(int i){
        currentFrame = i;
    }

    public boolean isPlayedOnce() {
        return playedOnce;
    }

    public void update(){
        //one second is 1000 millis.
        long millis = (System.currentTimeMillis() - startTime);
        if(millis >= delay) {
            currentFrame++;
            startTime = System.currentTimeMillis();
        }


        if(currentFrame == frames.length){
            currentFrame = 0;
            playedOnce = true;
        }
    }

    public Bitmap getImage(){

        if(GamePanel.getPlayer().isWalking()){
            update();
            return frames[currentDirection][currentFrame];

        }else{
            return frames[currentDirection][0];
        }
    }

    public int getCurrentFrame() {
        return currentFrame;
    }

    public void setCurrentDirection(String _currentDirection) {

        if(_currentDirection.equals("down")){
            currentDirection = 0;
        }else if(_currentDirection.equals("left")){
            currentDirection = 1;
        }else if(_currentDirection.equals("right")){
            currentDirection = 2;
        }else if(_currentDirection.equals("up")){
            currentDirection = 3;
        }

    }

}
