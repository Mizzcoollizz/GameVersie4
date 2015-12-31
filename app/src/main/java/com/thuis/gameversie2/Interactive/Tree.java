package com.thuis.gameversie2.Interactive;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.thuis.gameversie2.Map.CollisionObject;
import com.thuis.gameversie2.Map.MapHandler;
import com.thuis.gameversie2.MapScreen.GameView_Activity;
import com.thuis.gameversie2.Items.Tools.Axe;
import com.thuis.gameversie2.Items.Tools.Tool;
import com.thuis.gameversie2.R;

import java.util.Random;


/**
 * Created by Elize on 5-9-2015.
 */
public class Tree extends Interactive{

    private static final int MAX_GROWSTATE = 4;
    private int growState = 0;
    private static Bitmap[] imagesPerState = null;
    private int growDay = 0;
    private static final int GROW_DAYS_PER_STATE = 2;

    public Tree(long xLocation, long yLocation, int width, int height) {
        super(xLocation, yLocation, width, height);
        setImage();
    }

    private void setImage() {
        if(imagesPerState == null){
            imagesPerState = new Bitmap[MAX_GROWSTATE + 1];
            imagesPerState[0] = null; //TODO add sprout img
            imagesPerState[1] = BitmapFactory.decodeResource(GameView_Activity.getContext().getResources(), R.drawable.boom_stand1);
            imagesPerState[2] = BitmapFactory.decodeResource(GameView_Activity.getContext().getResources(), R.drawable.boom_stand2);
            imagesPerState[3] = BitmapFactory.decodeResource(GameView_Activity.getContext().getResources(), R.drawable.boom_stand3);
            imagesPerState[4] = BitmapFactory.decodeResource(GameView_Activity.getContext().getResources(), R.drawable.boom_omgehakt1);
        }
    }


    public Tree(long xLocation, long yLocation, int width, int height, int growState) {
        super(xLocation, yLocation, width, height);
        this.growState = growState;
        setImage();
    }

    @Override
    public void setCollision() {
       if(this.growState == 0 ){
            super.setCollisionObject(new CollisionObject(xLocation, yLocation, "tree", true, width, height));
        }else{
            super.setCollisionObject(new CollisionObject(xLocation, yLocation, "tree", false, width, height));
        }
    }

    @Override
    public void onInteraction() {
        //Todo do nothing?
        System.out.println("A tree");
    }

    @Override
    public void onInteraction(Tool _tool) {
        if(_tool instanceof Axe){
            int grade = new Random().nextInt(11);
//        for(int i = 0; i < growState; i++){
//            Lumber lumber = new Lumber(grade);
//            GamePanel.getInventory().add(lumber);
//        }
        }
    }

    @Override
    public Bitmap getImage() {
        return imagesPerState[growState];
    }

    @Override
    public void update(){
        this.growDay++;
        if(growDay == GROW_DAYS_PER_STATE){
            if(growState != MAX_GROWSTATE){
                growState++;
                setWidthAndHeight();
            }
            growDay = 0;
        }

    }

    private void setWidthAndHeight() {
        switch(growState){
            case 0:
                this.height = 1;
                break;
            case 1: this.height = 1;
                break;
            case 2: this.height = 2;
                break;
            case 3: this.height = 3;
                break;
        }
    }
}
