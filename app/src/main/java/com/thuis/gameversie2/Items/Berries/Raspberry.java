package com.thuis.gameversie2.Items.Berries;

import android.graphics.Bitmap;
import android.util.Log;

import com.thuis.gameversie2.MapScreen.GameView_Activity;
import com.thuis.gameversie2.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by Elize on 24-10-2015.
 */
public class Raspberry extends Berry {

    private static Bitmap[] growStateImages = new Bitmap[1];
    private static int growTime = 0;
    private static Bitmap image = null;
    private static Bitmap inventoryImage = null;
    private static final String PATH = "Berries/Raspberry/raspberry.json";
    private String itemImagePath = null;

    public Raspberry() {
        setImage();
        getRaspberryJson();
        name = "Raspberry";
        type = "Berry";
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
    protected void setItemImagePath(String itemImagePath) {
        this.itemImagePath = itemImagePath;
    }

    @Override
    public Bitmap getGrowStateImages(int state) {
        return growStateImages[state];
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
    public void setImage() {
        if(image == null){
            //TODO set image
            image = super.getImageFromResource(R.drawable.steen);
        }
        setInventoryImage();
    }


    @Override
    public Bitmap getImage(){
        return image;
    }


    private void getRaspberryJson(){
        if(growStateImages == null && growTime == 0 && itemImagePath == null){
            super.getBerryJson(this);
        }
            }

    @Override
    protected String getPath() {
        return PATH;
    }

}
