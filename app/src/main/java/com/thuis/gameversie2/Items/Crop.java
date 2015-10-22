package com.thuis.gameversie2.Items;

import android.graphics.Bitmap;

public class Crop extends Item {
	private String name = null;
	private String season = null;
	private int totalGrowTimeInDays = 0;
	private int growStages = 0;
	private Bitmap[] growingImages = null;
	private Bitmap image = null;
	private int growTimePerStage = 0;

	public Crop(String name, String season, int totalGrowTimeInDays, int growStages, Bitmap[] growingImages, Bitmap image) {
		this.name = name;
		this.season = season;
		this.totalGrowTimeInDays = totalGrowTimeInDays;
		this.growStages = growStages;
		this.growingImages = growingImages;
		this.image = image;
		calculateGrowTimePerStage(growStages, totalGrowTimeInDays);

	}

	public int getGrowTimePerStage() {
		return growTimePerStage;
	}

	private void calculateGrowTimePerStage(int growStages, int growTime) {
		int modulo = growTime % (growStages - 1);
		if(modulo == 0){
			this.growTimePerStage = growTime / (growStages - 1);
		}
	}

	public Bitmap getGrowingImage(int index) {
		try {
			return growingImages[index];
		}catch(IndexOutOfBoundsException ex){
			return null;
		}
	}

	@Override
	public void setImage() {

	}
}
