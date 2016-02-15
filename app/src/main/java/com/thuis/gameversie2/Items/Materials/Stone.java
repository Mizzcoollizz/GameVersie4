package com.thuis.gameversie2.Items.Materials;

import android.graphics.Bitmap;

public class Stone extends Material {

	private static Bitmap image = null;
	private static Bitmap inventoryImage = null;
	private static final String PATH = "materials/stone/stone.json";
	private static String name = null;
	private static String type = null;
	private static boolean jsonGathered = false;
	private static int standardSellPrice = 0;
	private static int standardBuyPrice = 0;

	public Stone(){
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
		Stone.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		Stone.type = type;
	}

	@Override
	public void setJsonGathered(boolean jsonGathered) {
		Stone.jsonGathered = jsonGathered;
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
		Stone.standardSellPrice = standardSellPrice;
	}

	@Override
	public int getStandardBuyPrice() {
		return standardBuyPrice;
	}

	@Override
	public void setStandardBuyPrice(int standardBuyPrice) {
		Stone.standardBuyPrice = standardBuyPrice;
	}

	@Override
	public boolean isJsonGathered() {
		return jsonGathered;
	}
}
