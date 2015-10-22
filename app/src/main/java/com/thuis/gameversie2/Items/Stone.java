package com.thuis.gameversie2.Items;

import android.graphics.BitmapFactory;

import com.thuis.gameversie2.MapScreen.GameView_Activity;
import com.thuis.gameversie2.R;

public class Stone extends Material {

	private final int SELL_PRICE = 10;
	private final int BUY_PRICE = 50;


	public Stone(Rock rock){
		grade = rock.grade;
		sellPrice = SELL_PRICE;
		buyPrice = BUY_PRICE;
		setImage();
	}

	@Override
	public void setImage() {
		image = BitmapFactory.decodeResource(GameView_Activity.getContext().getResources(), R.drawable.gehouwen_steen1);
	}
}
