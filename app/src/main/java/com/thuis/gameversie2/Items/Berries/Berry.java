package com.thuis.gameversie2.Items.Berries;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import com.thuis.gameversie2.Items.Crops.Crop;
import com.thuis.gameversie2.Items.Item;
import com.thuis.gameversie2.MapScreen.GameView_Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Created by Elize on 15-8-2015.
 */
public abstract class Berry extends Item {

    protected abstract void setGrowStateImages(Bitmap[] growStateImagesPaths);

    protected abstract void setGrowTime(int growTime);

    protected abstract void loadBerryImage(Bitmap image);

    public abstract int getGrowStagesAmount();

    public abstract void setGrowStagesAmount(int growStagesAmount);

//    public Bitmap getGrowStateImages(int index) {
//        try {
//            return BitmapFactory.decodeStream(GameView_Activity.getContext().getAssets().open(growStateImagesPaths[index]));
//        }catch (IndexOutOfBoundsException ex){
//            return null;
//        } catch (IOException e) {
//            return null;
//        }
//    }

    /**
     * Sets the growTime, itemImage and the growStateImages using the information given
     * by the json.
     */
    protected void getBerryJson(Berry berry){
        try {
            if (berry.isJsonGathered()) {
                return;
            }
                JSONObject json = getItemJSONObjectFromPath(berry.getPath());
                int growTime = json.getInt("growtime");
                berry.setGrowTime(growTime);
                berry.setName(json.getString("name"));
                berry.setType(json.getString("type"));
                berry.loadImage(super.getJsonImage(json.getString("image")));

                JSONArray growStateImagesInJson = json.getJSONArray("stateImages");
                Bitmap[] growStateImages = new Bitmap[growStateImagesInJson.length()];
                for (int image = 0; image < growStateImagesInJson.length(); image++) {
                    growStateImages[image] =
                            super.getJsonImage(
                                    growStateImagesInJson.get(image).toString());
                }
                berry.setGrowStateImages(growStateImages);
                int growStages = growStateImages.length;
                berry.setGrowTimePerStage(Crop.calculateGrowTimePerStage(growStages, growTime));
                berry.setGrowStagesAmount(growStages - 1);
                super.setPricing(berry);
                berry.setJsonGathered(true);
            }catch(JSONException e){
                e.printStackTrace();
                Log.i("tag", e.getMessage());
            }
    }


    public abstract Bitmap getGrowStateImages(int state);

    public abstract void setGrowTimePerStage(int i);
    public abstract int getGrowTimePerStage();

  }
