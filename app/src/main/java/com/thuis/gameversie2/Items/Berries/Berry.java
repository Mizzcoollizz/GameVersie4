package com.thuis.gameversie2.Items.Berries;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

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

    protected abstract String getPath();

    protected abstract void setGrowStateImages(Bitmap[] growStateImagesPaths);

    protected abstract void setGrowTime(int growTime);

    protected abstract void setItemImagePath(String itemImagePath);


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
     * Sets the growTime, itemImagePath and the growStateImages using the information given
     * by the json.
     */
    protected void getBerryJson(Berry berry){
        try{
            InputStream is = GameView_Activity.getContext().getAssets().open(berry.getPath());
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder stringBuilder = new StringBuilder();
            int i = 0;
            while ((i = rd.read()) != -1){
                stringBuilder.append((char) i );
            }
            JSONObject json = new JSONObject(stringBuilder.toString());

            berry.setGrowTime(json.getInt("growtime"));
            berry.name = name;
            berry.setItemImagePath(json.getString("image"));

            JSONArray growStateImagesInJson = json.getJSONArray("stateImages");
            Bitmap[] growStateImages = new Bitmap[growStateImagesInJson.length()];
            for(int image = 0; image < growStateImagesInJson.length(); image++){
                growStateImages[image] =
                        super.getJsonImage(
                                berry.getPath() + "/" + growStateImagesInJson.get(image).toString());
            }
            berry.setGrowStateImages(growStateImages);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("tag", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public abstract Bitmap getGrowStateImages(int state);
}
