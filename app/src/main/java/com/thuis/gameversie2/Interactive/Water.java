package com.thuis.gameversie2.Interactive;

import android.graphics.Bitmap;

import com.thuis.gameversie2.Items.Tools.Tool;

/**
 * Created by Elize on 5-3-2016.
 */
public class Water extends  Interactive{

    public Water(long xLocation, long yLocation, int width, int height) {
        super(xLocation, yLocation, width, height, true, false);
    }

    @Override
    public void onInteraction() {

    }

    @Override
    public void onInteraction(Tool tool) {

    }

    @Override
    public Bitmap getImage() {
        return null;
    }

    @Override
    public void update() {

    }

    @Override
    public void setCollision() {

    }
}
