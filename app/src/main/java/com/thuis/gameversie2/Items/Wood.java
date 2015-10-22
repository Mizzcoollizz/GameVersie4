package com.thuis.gameversie2.Items;

import java.util.Random;

public class Wood extends RawMaterial implements Spawnable_Item {

	private final int SELL_PRICE = 5;
	private final int BUY_PRICE = 25;

	public Wood(){
		setGrade();
		sellPrice = SELL_PRICE;
		buyPrice = BUY_PRICE;
		setImage();
	}
	public Material chop() {
		return null;
	}

	@Override
	public void setGrade() {
		Random random = new Random();
		grade = random.nextInt(11);
	}

	@Override
	public void setImage() {

	}
}
