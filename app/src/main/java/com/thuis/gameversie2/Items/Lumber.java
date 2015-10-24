package com.thuis.gameversie2.Items;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Lumber extends Material {

    private final int SELL_PRICE = 5;
    private final int BUY_PRICE = 25;
    private static Bitmap image = null;
    private static Bitmap inventoryImage = null;


    public Lumber(){
        setImage();
        sellPrice = SELL_PRICE;
        buyPrice = BUY_PRICE;
        setGrade();
        name = "Lumber";
        type = "Material";
    }

    public Lumber(int grade) {
        this.grade = grade;
        setImage();
        sellPrice = SELL_PRICE;
        buyPrice = BUY_PRICE;
        name = "Lumber";
    }

    @Override
    public void setImage() {
        //image =  BitmapFactory.decodeResource(GameView_Activity.getContext().getResources(), R.drawable.?);
        //TODO set image
    }

    @Override
    public Bitmap getInventoryImage() {
        return inventoryImage;
    }

    @Override
    public void setInventoryImage() {
        if(inventoryImage == null) {
            inventoryImage = super.createInventoryImage(image);
        }
    }

    @Override
    public Bitmap getImage() {
        return image;
    }

}
