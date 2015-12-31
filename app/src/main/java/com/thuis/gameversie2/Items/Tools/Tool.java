package com.thuis.gameversie2.Items.Tools;

import android.graphics.Bitmap;
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
import java.util.Arrays;

public abstract class Tool extends Item {

	private String type;

	private Arrays range;

	public void use() {

	}

	protected void getToolJson(Tool tool){
		try{

			JSONObject json = super.getItemJSONObjectFromPath(tool.getPath());
			tool.setName(json.getString("name"));
			tool.setType(json.getString("type"));
			tool.loadImage(super.getJsonImage(json.getString("image")));
			tool.setStandardBuyPrice(json.getInt("buyPrice"));
			tool.setStandardSellPrice(json.getInt("sellPrice"));
			tool.setJsonGathered(true);
			super.setPricing(tool);

			//TODO axe animations?

		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("tag", e.getMessage());
		}

	}

}
