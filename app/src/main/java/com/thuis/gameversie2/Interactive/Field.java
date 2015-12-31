package com.thuis.gameversie2.Interactive;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.thuis.gameversie2.MapScreen.GameView_Activity;
import com.thuis.gameversie2.Items.Crops.Crop;
import com.thuis.gameversie2.Items.Spawnable_Item;
import com.thuis.gameversie2.Items.Tools.Tool;
import com.thuis.gameversie2.R;

import java.util.Random;

/**
 * Created by Elize on 15-8-2015.
 */
public class Field extends Interactive{
    boolean isPlowed = false;
    boolean isWatered = false;
    boolean isSown = false;
    int growState = 0;
    int growDay = 0;
    Crop crop = null;
    Bitmap defaultImage = null;
    Spawnable_Item item = null;
    private static Bitmap wateredImage = null;
    private static Bitmap plowedImage = null;

    @Override
    public void update(){
        if(isSown()){
            growDay++;
            setGrowStateByGrowDay();
        }
        setIsWatered(false);
    }

    @Override
    public void setCollision() {
        //TODO set  collison?
    }

    private void setGrowStateByGrowDay() {
            if (growDay == crop.getGrowTimePerStage()) {
                growState++;
                growDay = 0;
            }
    }

    public void setItem(Spawnable_Item item){
        this.item = item;
    }

    public Spawnable_Item getItem(){
        return item;
    }


    public Field(long xLocation, long yLocation, int width, int height) {
       super(xLocation, yLocation, width, height);
        setDefaultFieldImage();
    }

    private void setDefaultFieldImage(){
        Random random = new Random();
        int number = random.nextInt(3);
        switch(number){
            case 0: defaultImage = BitmapFactory.decodeResource(GameView_Activity.getContext().getResources(), R.drawable.grond1 );
                break;
            case 1: defaultImage = BitmapFactory.decodeResource(GameView_Activity.getContext().getResources(), R.drawable.grond2 );
                break;
            case 2: defaultImage = BitmapFactory.decodeResource(GameView_Activity.getContext().getResources(), R.drawable.grond3 );
                break;
        }
        
        if(wateredImage == null){
            wateredImage = BitmapFactory.decodeResource(GameView_Activity.getContext().getResources(), R.drawable.watered_field);
        }
        
        if(plowedImage == null){
            plowedImage = BitmapFactory.decodeResource(GameView_Activity.getContext().getResources(), R.drawable.plowed_earth);
        }
        
        
    }

    public void setIsWatered(boolean isWatered) {
        this.isWatered = isWatered;
    }

    @Override
    public void onInteraction() {
        //TODO add interaction with field
    }

    @Override
    public void onInteraction(Tool tool) {

    }

    /**
     * This method is used for getting the image of the field.
     * First check if the field is watered, and if so, draw the watered layer over the default image.
     * Second check if the field is plowed, and if so, draw the plowed layer over the image;
     * @return the final bitmap
     */
    @Override
    public Bitmap getImage() {
        Bitmap image = defaultImage.copy(Bitmap.Config.ARGB_4444, true);
        Canvas canvas = new Canvas(image);
        if(isWatered()){
           canvas.drawBitmap(
                  wateredImage, 0, 0, null);
        }
        if(isPlowed()){
            canvas.drawBitmap(
                    plowedImage, 0, 0, null);
        }
        if(isSown() && getCrop() != null){
            canvas.drawBitmap(getCrop().getGrowingImage(getGrowState()), 0, 0, null);
        }
        return image;
    }

    public void setIsPlowed(boolean isPlowed) {
        this.isPlowed = isPlowed;
    }

    public void setIsSown(boolean isSown) {
        this.isSown = isSown;
    }

    public void addGrowDay() {
        this.growDay++;
    }

    public void setGrowState(int growState) {
        this.growState = growState;
    }

    public void setCrop(Crop crop) {
        this.crop = crop;
    }

    public boolean isPlowed() {
        return isPlowed;
    }

    public boolean isWatered() {
        return isWatered;
    }

    public boolean isSown() {
        return isSown;
    }

    public int getGrowState() {
        return growState;
    }

    public int getGrowDay() {
        return growDay;
    }

    public Crop getCrop() {
        return crop;
    }

    public boolean hasCrop(){
        if(item != null){
            return true;
        }else{
            return false;
        }
    }
}
