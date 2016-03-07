package com.thuis.gameversie2.Items.Crops;

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

public abstract class Crop extends Item {
//	private String name = null;
//	private String season = null;
//	private int totalGrowTimeInDays = 0;
//	private int growStages = 0;
//	private Bitmap[] growingImages = null;
//	private Bitmap image = null;
//	private int growTimePerStage = 0;

	public Crop(){

	}

//	public Crop(String name, String season, int totalGrowTimeInDays, int growStages, Bitmap[] growingImages, Bitmap image) {
//		this.name = name;
//		this.season = season;
//		this.totalGrowTimeInDays = totalGrowTimeInDays;
//		this.growStages = growStages;
//		this.growingImages = growingImages;
//		this.image = image;
//		calculateGrowTimePerStage(growStages, totalGrowTimeInDays);
//
//	}

//	public int getGrowTimePerStage() {
//		return growTimePerStage;
//	}

	/**
	 *
	 * @param growStages: the amount of growstages with the harvest stage included
	 * @param growTime: The growtime of the crop
	 * @return : The time the crop has to grow to get to the next growstate.
	 */
	public static int calculateGrowTimePerStage(int growStages, int growTime) {
		int modulo = growTime % (growStages - 1);
		if(modulo == 0){
			return growTime / (growStages - 1);
		}else{
			//TODO iets verzinnen voor oneven growtimes
		}
		return 0;
	}



	protected void getCropJson(Crop crop){
		try{
			if(crop.isJsonGathered()){
				return;
			}

			JSONObject json = super.getItemJSONObjectFromPath(crop.getPath());

			crop.setSeason(json.getString("season"));
			int totalGrowTimeInDays = json.getInt("growtime");
			crop.setGrowTime(totalGrowTimeInDays);
			crop.setName(json.getString("name"));
			crop.setType(json.getString("type"));
			crop.loadImage(Item.getJsonImage(json.getString("image")));
			crop.setStandardSellPrice(json.getInt("sellPrice"));
			crop.setStandardBuyPrice(json.getInt("buyPrice"));
			boolean perennial = json.getBoolean("perennial");
			crop.setPerennial(perennial);

			JSONArray growStateImagesInJson = json.getJSONArray("stateImages");
			Bitmap[] growStateImages = new Bitmap[growStateImagesInJson.length()];
			for(int image = 0; image < growStateImagesInJson.length(); image++){
				growStateImages[image] =
						Item.getJsonImage(
								crop.getPath() + "/" + growStateImagesInJson.get(image).toString());
			}
			crop.setGrowStateImages(growStateImages);
			int growStages = growStateImages.length;
			crop.setGrowStagesAmount(growStages - 1);

			crop.setGrowTimePerStage(calculateGrowTimePerStage(growStages, totalGrowTimeInDays));
			super.setPricing(crop);
			crop.setJsonGathered(true);
		} catch (JSONException e) {
			e.printStackTrace();
			Log.i("tag", e.getMessage());
		}
	}

	protected abstract void setGrowStateImages(Bitmap[] growStateImages);

	protected abstract void setGrowTime(int growtime);

	protected abstract void setGrowStagesAmount(int i);

	protected abstract void setSeason(String season);

	protected abstract void setGrowTimePerStage(int i);

	public abstract void setPerennial(boolean perennial);

	public abstract boolean isPerennial();

	public abstract Bitmap getGrowingImage(int index);

	public abstract int getGrowTimePerStage();

	public abstract int getGrowStagesAmount();

}
