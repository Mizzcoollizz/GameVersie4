package com.thuis.gameversie2.Items.Raw_Materials;

import android.graphics.Bitmap;

public class Ore extends RawMaterial {
	private static Bitmap image = null;
	private static Bitmap inventoryImage = null;
	private static final String PATH = "materials/raw_materials/ore/ore.json";
	private static String name = null;
	private static String type = null;
	private static boolean jsonGathered = false;
	private static int standardSellPrice = 0;
	private static int standardBuyPrice = 0;

	public Ore(){
		setRandomGrade();
		super.getMaterialJson(this);

	}

	@Override
	public  Bitmap getImage() {
		return image;
	}

	@Override
	public  Bitmap getInventoryImage() {
		return inventoryImage;
	}

	@Override
	public void setInventoryImage() {
		inventoryImage = super.createInventoryImage(image);
	}


	public String getPath() {
		return PATH;
	}

	public  String getName() {
		return name;
	}

	public  void setName(String name) {
		Ore.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		Ore.type = type;
	}

	@Override
	public void setJsonGathered(boolean jsonGathered) {
		Ore.jsonGathered = jsonGathered;
	}

	@Override
	protected void loadImage(Bitmap _image) {
		image = _image;
		setInventoryImage();
	}

	@Override
	public int getStandardSellPrice() {
		return standardSellPrice;
	}

	@Override
	public void setStandardSellPrice(int standardSellPrice) {
		Ore.standardSellPrice = standardSellPrice;
	}

	@Override
	public int getStandardBuyPrice() {
		return standardBuyPrice;
	}

	@Override
	public void setStandardBuyPrice(int standardBuyPrice) {
		Ore.standardBuyPrice = standardBuyPrice;
	}

	@Override
	public boolean isJsonGathered() {
		return jsonGathered;
	}
}
