package com.thuis.gameversie2.Items;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Ore extends RawMaterial {

	private final int BUY_PRICE = 10;
	private final int SELL_PRICE = 5;
	private static Bitmap image = null;
	private static Bitmap inventoryImage = null;

	public Ore(){
		name = "Ore";
		type = "Raw material";
		buyPrice = BUY_PRICE;
		sellPrice = SELL_PRICE;
		setImage();
	}
	public static Material smash(Ore ore) {
		//TODO smash
		return null;
	}

	@Override
	public void setImage() {
		//TODO set image
		setInventoryImage();
	}

	@Override
	public Bitmap getInventoryImage() {
		return inventoryImage;
	}

	@Override
	public void setInventoryImage() {
		if(inventoryImage == null) {
			inventoryImage = super.createInventoryImage(image);
		}
	}

	@Override
	public Bitmap getImage() {
		return image;
	}
}
