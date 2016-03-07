package com.thuis.gameversie2.Interactive;

import android.graphics.Bitmap;
import android.util.Log;


import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Items.Berries.Berry;
import com.thuis.gameversie2.Items.Item;
import com.thuis.gameversie2.Items.Tools.Axe;
import com.thuis.gameversie2.Items.Tools.Tool;

/**
 * Created by Elize on 15-8-2015.
 */
public class Bush extends Interactive{
    private Berry berry = null;
    private int state = 0;
    private Bitmap[] images = null;
    private int growDay = 0;
    private boolean regrown = false;

    public Bush(long xLocation, long yLocation, int width, int height, boolean _regrown, Berry _berry) {
        //Collision and lowObject
        super(xLocation, yLocation, width, height, true, true);
        this.regrown = _regrown;
        this.berry = _berry;
        setStartProperties();
    }

    private void setStartProperties() {
        if(regrown){
            setState(berry.getGrowStagesAmount());
        }
    }

    private void harvest(){
        //Todo check create Bush interaction
        if(regrown){
            if(GamePanel.getInventory().add(Item.getNewInstance(getBerry()))){
                Log.i("tag", "true");
                this.regrown = false;
                this.growDay = 0;
                this.state = 0;
            }
        }
    }


    @Override
    public void onInteraction() {
        harvest();
    }

    @Override
    public void onInteraction(Tool tool) {
        if(tool instanceof Axe){
            //TODO remove this bush
        }
    }

    @Override
    public Bitmap getImage() {
        return berry.getGrowStateImages(getState());
    }

    @Override
    public void update() {
        grow();
    }

    public void grow(){
        if(state != getBerry().getGrowStagesAmount()){
            growDay++;
            if(growDay == getBerry().getGrowTimePerStage() && state != getBerry().getGrowStagesAmount()){
                state++;
                growDay = 0;
                if(state == getBerry().getGrowStagesAmount()){
                    regrown = true;
                }
            }
        }
    }

    @Override
    public void setCollision() {

    }

    public void setGrowDay(int growDay) {
        this.growDay = growDay;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Berry getBerry() {
        return berry;
    }

    public int getState() {
        return state;
    }

    public Bitmap[] getImages() {
        return images;
    }

    public int getGrowDay() {
        return growDay;
    }


}
