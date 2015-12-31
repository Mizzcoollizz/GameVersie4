package com.thuis.gameversie2.Map;

import android.graphics.Rect;
import android.util.Log;

import com.thuis.gameversie2.Character.MainCharacter;
import com.thuis.gameversie2.GamePanel;

/**
 * Created by Elize on 10-8-2015.
 */
public class CollisionObject {

    private long xLocation = 0;
    private long yLocation = 0;
    private String type = null;
    private boolean lowObject = false;
    private float height = 0f;
    private float width = 0f;


    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public CollisionObject(long xLocation, long yLocation, String type, boolean lowObject, float height, float width) {
        this.xLocation = xLocation;
        this.yLocation = yLocation;
        this.type = type;
        this.lowObject = lowObject;
        this.height = height;
        this.width = width;
    }
//
//    public CollisionObject(long xLocation, long yLocation, String type, boolean lowObject) {
//        this.xLocation = xLocation;
//        this.yLocation = yLocation;
//        this.type = type;
//        this.lowObject = lowObject;
//
//    }

    public long getXLocation() {
        return xLocation;
    }

    public long getYLocation() {
        return yLocation;
    }

    public boolean checkCollision(){
        MainCharacter player = GamePanel.getPlayer();
        int leftSide = (int)this.getXLocation();
        int rightSide = (int)this.getXLocation() + (int) this.getWidth();
        int topSide = (int) this.getYLocation();
        int bottomSide = (int) this.getYLocation() + (int) this.getHeight();

        Rect playerRect = player.getRect();
        Rect rect = new Rect(leftSide, topSide,rightSide, bottomSide);
        if(checkIntersection(rect, playerRect, player.getDirection())){
            return true;
        }else{
            return false;
        }
    }

    public static boolean checkIntersection(Rect collisionRect, Rect player, String direction) {
        if(direction.equals("left") && player.left <= collisionRect.right){
            return true;
        }else if(direction.equals("right") && player.right >= collisionRect.left){
            return true;
        }else if(direction.equals("down") && player.bottom >= collisionRect.top){
            return true;
        }else if(direction.equals("up") && player.top <= collisionRect.bottom){
            return true;
        }else{
            return false;
        }
    }

}
