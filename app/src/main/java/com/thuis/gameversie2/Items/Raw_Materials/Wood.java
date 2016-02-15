package com.thuis.gameversie2.Items.Raw_Materials;

import android.graphics.Bitmap;

import com.thuis.gameversie2.Items.Spawnable_Item;

public class Wood extends RawMaterial implements Spawnable_Item {

	private static Bitmap image = null;
	private static Bitmap inventoryImage = null;
	private static final String PATH = "materials/raw_materials/wood/wood.json";
	private static String NAME = null;
	private static String TYPE = null;
	private static boolean jsonGathered = false;
	private static int standardSellPrice = 0;
	private static int standardBuyPrice = 0;

	public Wood(){
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
		return NAME;
	}

	public  void setName(String NAME) {
		Wood.NAME = NAME;
	}

	public String getType() {
		return TYPE;
	}

	public void setType(String TYPE) {
		Wood.TYPE = TYPE;
	}

	@Override
	public void setJsonGathered(boolean jsonGathered) {
		Wood.jsonGathered = jsonGathered;
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
		Wood.standardSellPrice = standardSellPrice;
	}

	@Override
	public int getStandardBuyPrice() {
		return standardBuyPrice;
	}

	@Override
	public void setStandardBuyPrice(int standardBuyPrice) {
		Wood.standardBuyPrice = standardBuyPrice;
	}

	@Override
	public boolean isJsonGathered() {
		return jsonGathered;
	}
}
