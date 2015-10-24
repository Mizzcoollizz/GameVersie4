package com.thuis.gameversie2.Items.Tools;

import android.graphics.Bitmap;

import com.thuis.gameversie2.Items.Item;
import com.thuis.gameversie2.Items.Tools.Tool;

/**
 * Created by Elize on 7-9-2015.
 */
public class Axe extends Tool {

    private final int BUY_PRICE = 1000;
    private final int SELL_PRICE = 500;
    private static Bitmap image = null;
    private static Bitmap inventoryImage = null;

    public Axe(int grade){
        buyPrice = getBuyPriceByGrade(grade, BUY_PRICE);
        sellPrice = getSellPriceByGrade(grade, SELL_PRICE);
        setImage();

    }

    @Override
    public void setImage() {
        //TODO set image
        if(image == null){

        }
        setInventoryImage();
    }

    @Override
    public Bitmap getImage() {
        return image;
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
}
