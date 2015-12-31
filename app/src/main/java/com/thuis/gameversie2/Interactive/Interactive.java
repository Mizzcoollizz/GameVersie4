package com.thuis.gameversie2.Interactive;

import android.graphics.Bitmap;
import android.graphics.Rect;

import com.thuis.gameversie2.Items.Tools.Tool;
import com.thuis.gameversie2.Map.CollisionObject;
import com.thuis.gameversie2.Map.MapHandler;

/**
 * Created by Elize on 15-8-2015.
 */
public abstract class Interactive {
    long xLocation = 0;
    long yLocation = 0;
    int width = 0;
    int height = 0;
    private CollisionObject collisionObject = null;

    public Interactive(long xLocation, long yLocation, int width, int height) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.width = width;
        this.height = height;
    }

    public long getXLocation() {
        return xLocation;
    }

    public long getYLocation() {
        return yLocation;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Rect getRect() {
        return new Rect((int)getXLocation(), (int)getYLocation(), (int)getXLocation() + getWidth(), (int)getYLocation() + getHeight());
    }

    public abstract void onInteraction();

    public abstract void onInteraction(Tool tool);

    public abstract Bitmap getImage();

    public abstract void update();

    protected  void setCollisionObject(CollisionObject object){
        if(this.collisionObject != null){
            MapHandler.getCurrentMap().removeCollisionObject(this.collisionObject);
        }
        this.collisionObject = object;
        MapHandler.getCurrentMap().addcollisionObject(collisionObject);
    };

    public abstract void setCollision();


}
