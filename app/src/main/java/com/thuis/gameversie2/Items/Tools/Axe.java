package com.thuis.gameversie2.Items.Tools;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.thuis.gameversie2.Items.Item;
import com.thuis.gameversie2.Items.Tools.Tool;

/**
 * Created by Elize on 7-9-2015.
 */
public class Axe extends Tool {

    private static Bitmap image = null;
    private static Bitmap inventoryImage = null;
    private static int standardSellPrice = 0;
    private static int standardBuyPrice = 0;
    private static String NAME = null;
    private static String TYPE = null;
    private boolean jsonGathered = false;
    private static final String PATH = "Tools/Axe/Axe.json";


    public Axe(int grade){
        this.grade = grade;
        getToolJson();
    }

    private void getToolJson(){
        if(!jsonGathered) {
            super.getToolJson(this);
        }
    }

    @Override
    public boolean isJsonGathered() {
        return jsonGathered;
    }


    @Override
    public Bitmap getImage() {
        return image;
    }

    @Override
    public String getName() {
        return TYPE;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    protected void setStandardSellPrice(int i) {
        standardSellPrice = i;
    }

    @Override
    protected void setStandardBuyPrice(int i) {
        standardBuyPrice = i;
    }

    @Override
    protected void setType(String type) {
        TYPE = type;
    }

    @Override
    protected void setName(String name) {
        NAME = name;
    }

    @Override
    protected void setJsonGathered(boolean bool) {
        jsonGathered = bool;
    }

    @Override
    protected void loadImage(Bitmap _image) {
        image = _image;
        setInventoryImage();
    }

    @Override
    protected String getPath() {
        return PATH;
    }

    @Override
    protected int getStandardSellPrice() {
        return standardSellPrice;
    }

    @Override
    protected int getStandardBuyPrice() {
        return standardBuyPrice;
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
