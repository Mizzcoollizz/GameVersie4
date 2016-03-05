package com.thuis.gameversie2.Items.Berries;

import android.graphics.Bitmap;

/**
 * Created by Elize on 24-10-2015.
 */
public class Raspberry extends Berry {

    private static Bitmap[] growStateImages = new Bitmap[1];
    private static int growTime = 0;
    private static Bitmap image = null;
    private static Bitmap inventoryImage = null;
    private static final String PATH = "berries/raspberry/raspberry.json";
    private static String name = "Raspberry";
    private static String type = "Berry";
    private static boolean jsonGathered = false;
    private static int growTimePerStage = 0;
    private static int standardSellPrice = 0;
    private static int standardBuyPrice = 0;
    private static int growStagesAmount = 0;

    public void setJsonGathered(boolean jsonGathered) {
        Raspberry.jsonGathered = jsonGathered;
    }

    public Raspberry() {
        super.getBerryJson(this);
    }

    @Override
    public  int getGrowStagesAmount() {
        return growStagesAmount;
    }

    @Override
    public void setGrowStagesAmount(int growStagesAmount) {
        Raspberry.growStagesAmount = growStagesAmount;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
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
        Raspberry.type = type;
    }

    @Override
    protected void setName(String name) {
        Raspberry.name = name;
    }

    @Override
    protected void setGrowStateImages(Bitmap[] growStateImages) {
        Raspberry.growStateImages = growStateImages;
    }

    @Override
    protected void setGrowTime(int growTime) {
        Raspberry.growTime = growTime;
    }

    @Override
    protected void loadBerryImage(Bitmap image) {
        this.image = image;
        setInventoryImage();
    }

    @Override
    public Bitmap getGrowStateImages(int state) {
        return growStateImages[state];
    }

    @Override
    public void setGrowTimePerStage(int i) {
        growTimePerStage = i;
    }

    @Override
    public int getGrowTimePerStage() {
        return growTimePerStage;

    }

    @Override
    public Bitmap getInventoryImage() {
        return inventoryImage;
    }

    @Override
    public void setInventoryImage() {
        if(inventoryImage == null ){
            inventoryImage = super.createInventoryImage(image);
        }
    }

    @Override
    public Bitmap getImage(){
        return image;
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
    protected void loadImage(Bitmap _image) {
        image = _image;
        setInventoryImage();
    }

    @Override
    public boolean isJsonGathered() {
        return jsonGathered;
    }


}
