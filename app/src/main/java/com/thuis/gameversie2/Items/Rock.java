package com.thuis.gameversie2.Items;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.thuis.gameversie2.MapScreen.GameView_Activity;
import com.thuis.gameversie2.R;

import java.util.Random;

public class Rock extends RawMaterial implements Spawnable_Item {

	private final int SELL_PRICE = 5;
	private final int BUY_PRICE = 25;
	private static Bitmap image = null;
	private static Bitmap inventoryImage = null;

	public Material smash() {
		return new Stone(this);
	}

	public Rock(){
		setImage();
		sellPrice = SELL_PRICE;
		buyPrice = BUY_PRICE;
		setGrade();
		name = "Rock";
		type = "Raw material";
	}

	@Override
	public Bitmap getInventoryImage() {
		return inventoryImage;
	}

	@Override
	public void setInventoryImage() {
		if(inventoryImage == null){
			inventoryImage = super.createInventoryImage(image);
		}
	}

	@Override
	public Bitmap getImage() {
		return image;
	}


	@Override
	public void setImage() {
		if(image == null) {
			//image = BitmapFactory.decodeResource(GameView_Activity.getContext().getResources(), R.drawable.steen);
			image = super.getImageFromResource(R.drawable.steen);
		}
		setInventoryImage();
	}
}
