package com.thuis.gameversie2.Items;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import com.thuis.gameversie2.R;

import java.util.Random;

public class Wood extends RawMaterial implements Spawnable_Item {

	private final int SELL_PRICE = 5;
	private final int BUY_PRICE = 25;
	private static Bitmap image = null;
	private static Bitmap inventoryImage = null;

	public Wood(){
		setGrade();
		sellPrice = SELL_PRICE;
		buyPrice = BUY_PRICE;
		setImage();
		name = "Wood";
		type = "Raw material";
	}

	public Material chop() {
		return null;
	}
	

	@Override
	public Bitmap getImage() {
		return image;
	}

	@Override
	public void setGrade() {
		Random random = new Random();
		grade = random.nextInt(11);
	}


	public void setImage() {
		//TODO set hout image
		//image = super.getImageFromResource(R.drawable.);
	}

	@Override
	public Bitmap getInventoryImage() {
		return inventoryImage;
	}

	@Override
	public void setInventoryImage() {
		inventoryImage = super.createInventoryImage(image);
	}
}
