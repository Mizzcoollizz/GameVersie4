package com.thuis.gameversie2.Items.Raw_Materials;

import android.graphics.Bitmap;

/**
 * Created by Elize on 3-11-2015.
 */
public class Gold_Ore extends RawMaterial {

    private static Bitmap image = null;
    private static Bitmap inventoryImage = null;
    private static final String PATH = "materials/raw_materials/ore/gold_ore/gold_ore.json";
    private static String name = null;
    private static String type = null;
    private static boolean jsonGathered = false;
    private static int standardSellPrice = 0;
    private static int standardBuyPrice = 0;

    public Gold_Ore(){
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
        Gold_Ore.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        Gold_Ore.type = type;
    }

    @Override
    public void setJsonGathered(boolean jsonGathered) {
        Gold_Ore.jsonGathered = jsonGathered;
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
        Gold_Ore.standardSellPrice = standardSellPrice;
    }

    @Override
    public int getStandardBuyPrice() {
        return standardBuyPrice;
    }

    @Override
    public void setStandardBuyPrice(int standardBuyPrice) {
        Gold_Ore.standardBuyPrice = standardBuyPrice;
    }

    @Override
    public boolean isJsonGathered() {
        return jsonGathered;
    }




}
