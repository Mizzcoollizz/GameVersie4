package com.thuis.gameversie2.Items;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.thuis.gameversie2.MapScreen.GameView_Activity;
import com.thuis.gameversie2.R;

public class Stone extends Material {

	private final int SELL_PRICE = 10;
	private final int BUY_PRICE = 50;
	private static  Bitmap image = null;
	private static Bitmap inventoryImage = null;


	public Stone(Rock rock){
		grade = rock.grade;
		sellPrice = SELL_PRICE;
		buyPrice = BUY_PRICE;
		setImage();
		name = "Stone";
		type = "Material";
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
			image = super.getImageFromResource(R.drawable.gehouwen_steen1);
		}
		setInventoryImage();
	}
}
