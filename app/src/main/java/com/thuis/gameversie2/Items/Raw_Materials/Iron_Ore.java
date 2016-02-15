package com.thuis.gameversie2.Items.Raw_Materials;

import android.graphics.Bitmap;

/**
 * Created by Elize on 3-11-2015.
 */
public class Iron_Ore extends RawMaterial {

    private static Bitmap image = null;
    private static Bitmap inventoryImage = null;
    private static final String PATH = "materials/raw_materials/ore/iron_ore/iron_ore.json";
    private static String NAME = null;
    private static String TYPE = null;
    private static boolean jsonGathered = false;
    private static int standardSellPrice = 0;
    private static int standardBuyPrice = 0;

    public Iron_Ore(){
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
        return NAME;
    }

    public  void setName(String NAME) {
        Iron_Ore.NAME = NAME;
    }

    public String getType() {
        return TYPE;
    }

    public void setType(String TYPE) {
        Iron_Ore.TYPE = TYPE;
    }

    @Override
    public void setJsonGathered(boolean jsonGathered) {
        Iron_Ore.jsonGathered = jsonGathered;
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
        Iron_Ore.standardSellPrice = standardSellPrice;
    }

    @Override
    public int getStandardBuyPrice() {
        return standardBuyPrice;
    }

    @Override
    public void setStandardBuyPrice(int standardBuyPrice) {
        Iron_Ore.standardBuyPrice = standardBuyPrice;
    }

    @Override
    public boolean isJsonGathered() {
        return jsonGathered;
    }



}
