package com.thuis.gameversie2.Items.Crops;

import android.graphics.Bitmap;

/**
 * Created by Elize on 30-10-2015.
 */
public class Tomato extends Crop {

    private static String season = null;
    private static int totalGrowTimeInDays = 0;
    private static int growStages = 0;
    private static Bitmap[] growStateImages = null;
    private static Bitmap image = null;
    private static Bitmap inventoryImage = null;
    private static int growTimePerStage = 0;
    private static final String PATH = "planten/groente/tomaten/tomato.json";
    private static String name = "Wheat";
    private static String type = "Crop";
    private static int standardSellPrice = 0;
    private static int standardBuyPrice = 0;
    private static boolean jsonGathered = false;
    private static boolean perennial = false;


    public Tomato(int grade){
        this.grade = grade;
        getJson();
    }


    private void getJson(){
        if(!jsonGathered) {
            super.getCropJson(this);
        }
    }


    @Override
    public boolean isJsonGathered() {
        return jsonGathered;
    }

    @Override
    protected void setGrowStages(int i) {
        growStages = i;
    }

    @Override
    protected void setType(String _type) {
        type = _type;
    }

    @Override
    protected void setName(String _name) {
        name = _name;
    }

    @Override
    protected void setSeason(String _season) {
        season = _season;
    }

    @Override
    protected void setGrowTimePerStage(int i) {
        growTimePerStage = i;
    }

    @Override
    public void setPerennial(boolean _perennial) {
        perennial = _perennial;
    }

    @Override
    public boolean isPerennial() {
        return perennial;
    }

    @Override
    public Bitmap getGrowingImage(int index) {
        return growStateImages[index];
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
    protected void setGrowStateImages(Bitmap[] _growStateImages) {
        growStateImages = _growStateImages;
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
    protected void setGrowTime(int growtime) {
        totalGrowTimeInDays = growtime;
    }

    @Override
    protected void setJsonGathered(boolean bool) {
        jsonGathered = bool;
    }

      @Override
    public Bitmap getInventoryImage() {
        return inventoryImage;
    }

    @Override
    public void setInventoryImage() {
        inventoryImage = super.createInventoryImage(image);
    }

    @Override
    public Bitmap getImage() {
        return image;
    }

    @Override
    public int getGrowTimePerStage() {
        return growTimePerStage;
    }

}
