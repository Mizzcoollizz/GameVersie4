package com.thuis.gameversie2.Items;

import android.graphics.BitmapFactory;

import com.thuis.gameversie2.MapScreen.GameView_Activity;
import com.thuis.gameversie2.R;

import java.util.Random;

public class Rock extends RawMaterial implements Spawnable_Item {

	private final int SELL_PRICE = 5;
	private final int BUY_PRICE = 25;

	public Material smash() {
		return new Stone(this);
	}

	public Rock(){
		setImage();
		sellPrice = SELL_PRICE;
		buyPrice = BUY_PRICE;
		setGrade();
		name = "Rock";
	}

	@Override
	public void setGrade() {
		Random random = new Random();
		grade = random.nextInt(11);
	}

	@Override
	public void setImage() {
		image = BitmapFactory.decodeResource(GameView_Activity.getContext().getResources(), R.drawable.steen);
	}
}
