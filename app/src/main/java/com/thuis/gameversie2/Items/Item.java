package com.thuis.gameversie2.Items;

import android.graphics.Bitmap;

import java.util.Random;

public abstract class Item {

	private static final int MAX_GRADE_RANDOM = 11;
	protected int sellPrice = 0;

	protected int buyPrice = 0;

	protected int grade = 0;

	protected String name = null;

	Bitmap image = null;

	public Bitmap getImage(){
		return image;
	}

	public abstract void setImage();

	public boolean isEqualItem(Item item){
		if(this.grade == item.grade && this.getClass().equals(item.getClass())){
			return true;
		}else{
			return false;
		}
	}

	public int getGrade() {
		return grade;
	}

	public String getName() {
		return name;
	}

	public void setGrade() {
		Random random = new Random();
		grade = random.nextInt(MAX_GRADE_RANDOM);
	}
}
