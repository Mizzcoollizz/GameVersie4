package com.thuis.gameversie2.Items.Materials;

import android.util.Log;

import com.thuis.gameversie2.Items.Item;

import org.json.JSONException;
import org.json.JSONObject;

public abstract class Material extends Item {

    protected void getMaterialJson(Material material){
        if(material.isJsonGathered()){
            return;
        }else{
            try {
                JSONObject json = super.getItemJSONObjectFromPath(material.getPath());
                material.setStandardBuyPrice(json.getInt("buyPrice"));
                material.loadImage(getJsonImage(json.getString("image")));
                material.setStandardBuyPrice(json.getInt("sellPrice"));
                material.setJsonGathered(true);
                material.setType(json.getString("type"));
                material.setName(json.getString("name"));
                material.setPricing(material);
                //TODO load intearction animation?
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
