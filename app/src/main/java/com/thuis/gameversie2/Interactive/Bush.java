package com.thuis.gameversie2.Interactive;

import android.graphics.Bitmap;


import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Items.Berries.Berry;
import com.thuis.gameversie2.Items.Item;
import com.thuis.gameversie2.Items.Tools.Axe;
import com.thuis.gameversie2.Items.Tools.Tool;

/**
 * Created by Elize on 15-8-2015.
 */
public class Bush extends Interactive{
    int reGrowTime = 0;
    Berry berry = null;
    int state = 0;
    Bitmap[] images = null;
    int growDay = 0;
    boolean regrown = false;

    public Bush(long xLocation, long yLocation, int width, int height, boolean _regrown, Berry _berry) {
        super(xLocation, yLocation, width, height);
        this.regrown = _regrown;
        this.berry = _berry;
    }

    private void harvest(){
        //Todo check create Bush interaction
        if(regrown){
            if(GamePanel.getInventory().add(Item.getNewInstance(getBerry()))){
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

    public int getReGrowTime() {
        return reGrowTime;
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
