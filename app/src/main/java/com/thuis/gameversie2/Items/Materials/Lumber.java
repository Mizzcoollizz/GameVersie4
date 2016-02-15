package com.thuis.gameversie2.Items.Materials;

import android.graphics.Bitmap;

public class Lumber extends Material {

    private static Bitmap image = null;
    private static Bitmap inventoryImage = null;
    private static final String PATH = "materials/lumber/lumber.json";
    private static String name = null;
    private static String type = null;
    private static boolean jsonGathered = false;
    private static int standardSellPrice = 0;
    private static int standardBuyPrice = 0;

    public Lumber(){
        setRandomGrade();
        super.getMaterialJson(this);

    }

    @Override
    public  Bitmap getImage() {
        return image;
    }

    @Override
    public  Bitmap getInventoryImage() {
        return inventoryImage;
    }

    @Override
    public void setInventoryImage() {
        inventoryImage = super.createInventoryImage(image);
    }


    public String getPath() {
        return PATH;
    }

    public  String getName() {
        return name;
    }

    public  void setName(String name) {
        Lumber.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        Lumber.type = type;
    }

    @Override
    public void setJsonGathered(boolean jsonGathered) {
        Lumber.jsonGathered = jsonGathered;
    }

    @Override
    protected void loadImage(Bitmap _image) {
        image = _image;
        setInventoryImage();
    }

    @Override
    public int getStandardSellPrice() {
        return standardSellPrice;
    }

    @Override
    public void setStandardSellPrice(int standardSellPrice) {
        Lumber.standardSellPrice = standardSellPrice;
    }

    @Override
    public int getStandardBuyPrice() {
        return standardBuyPrice;
    }

    @Override
    public void setStandardBuyPrice(int standardBuyPrice) {
        Lumber.standardBuyPrice = standardBuyPrice;
    }

    @Override
    public boolean isJsonGathered() {
        return jsonGathered;
    }

}
