package com.thuis.gameversie2.Interactive;

import android.content.Context;
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
    boolean lowObject = false;
    boolean collision = false;
    private CollisionObject collisionObject = null;

    public Interactive(long xLocation, long yLocation, int width, int height) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.width = width;
        this.height = height;
    }

    public Interactive(long xLocation, long yLocation, int width, int height, boolean _collision, boolean _lowObject) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.width = width;
        this.height = height;
        this.collision = _collision;
        this.lowObject = _lowObject;
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
//        if(this.collisionObject != null){
//            MapHandler.getCurrentMap().removeCollisionObject(this.collisionObject);
//        }
//        this.collisionObject = object;
//        MapHandler.getCurrentMap().addcollisionObject(collisionObject);
    }

    public abstract void setCollision();

    /**
     * For performance reasons, the collision object should only be created when the interactive enters the screen.
     */
    public void onScreenEnter(){
        if(collision && collisionObject == null){
            collisionObject = new CollisionObject(xLocation, yLocation, lowObject, width, height );
            MapHandler.getCurrentMap().addcollisionObject(collisionObject);
        }
    }

    /**
     * For performance reasons, the collision object should be deleted when leaving the screen.
     */
    public void onScreenLeave(){
        if(this.collisionObject != null) {
            MapHandler.getCurrentMap().removeCollisionObject(collisionObject);
            collisionObject = null;
        }
    }

    @Override
    public boolean equals(Object o) {
        if(o == null || !this.getClass().equals(o.getClass())){
            return false;
        }
        Interactive interactive = (Interactive) o;
        return this.getRect().equals(interactive.getRect());
    }
}
