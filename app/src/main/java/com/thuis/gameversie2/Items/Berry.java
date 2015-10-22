package com.thuis.gameversie2.Items;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

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
public class Berry extends Item {

    String[] growStateImagesPaths = new String[1];
    int growTime = 0;
    private String imagePath = null;

    public Berry(String _name) {
        getBerryJson(_name);
        name = _name;
    }


    public Bitmap getGrowStateImages(int index) {
        try {
            return BitmapFactory.decodeStream(GameView_Activity.getContext().getAssets().open(growStateImagesPaths[index]));
        }catch (IndexOutOfBoundsException ex){
            return null;
        } catch (IOException e) {
            return null;
        }
    }



    public void getBerryJson(String name) {
        JSONObject json = null;
        String path = "Berries/";
        if(name.equals("Raspberry")){
            path += "Raspberry/raspberry.json";
        }

        try{
                InputStream is = GameView_Activity.getContext().getAssets().open(path);
                BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
                StringBuilder stringBuilder = new StringBuilder();
                int i = 0;
                while ((i = rd.read()) != -1){
                    stringBuilder.append((char) i );
                }
                json = new JSONObject(stringBuilder.toString());

            this.growTime = json.getInt("growtime");
            this.name = name;
            this.imagePath = json.getString("image");

            JSONArray growStateImagesInJson = json.getJSONArray("stateImages");
            this.growStateImagesPaths = new String[growStateImagesInJson.length()];
            for(int image = 0; image < growStateImagesInJson.length(); image++){
                this.growStateImagesPaths[image] =
                        path + "/" + growStateImagesInJson.get(image).toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("tag", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bitmap getJsonImage(String path) throws IOException{
        InputStream is = GameView_Activity.getContext().getAssets().open(path);
        return BitmapFactory.decodeStream(is);
    }

    @Override
    public void setImage() {

    }

    @Override
    public Bitmap getImage(){
        //Todo check if working
        return BitmapFactory.decodeFile(imagePath);
    }
}
