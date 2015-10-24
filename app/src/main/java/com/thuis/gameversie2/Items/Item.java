package com.thuis.gameversie2.Items;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Main_Menu_Activity;
import com.thuis.gameversie2.MapScreen.GameView_Activity;
import com.thuis.gameversie2.R;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public abstract class Item {

	private static final int MAX_GRADE_RANDOM = 11;

	protected int sellPrice = 0;

	protected int buyPrice = 0;

	protected int grade = 0;

	protected String name = null;

	public static Bitmap borderImageBitmap = null;

	static{
		setBorderImageBitmap();
	}

	protected String type = "None";

	public Item(){}

	private static boolean setBorderImageBitmap() {
		if(borderImageBitmap == null && Main_Menu_Activity.getContext() != null) {
			Bitmap borderImage = BitmapFactory.decodeResource(Main_Menu_Activity.getContext().getResources(), R.drawable.inventory_item_border);
			borderImageBitmap = Bitmap.createScaledBitmap(borderImage, 72, 72, false);
		return true;
		}
		return false;
	}

	public abstract void setImage();
	public abstract Bitmap getInventoryImage();
	public abstract void setInventoryImage();

	public Bitmap createInventoryImage(Bitmap image) {
			setBorderImageBitmap();
			Bitmap newImage = borderImageBitmap.copy(Bitmap.Config.ARGB_4444, true);
			Canvas canvas = new Canvas(newImage);
			canvas.drawBitmap(image, 0, 0, null);

		return newImage;

	}

	public abstract Bitmap getImage();

	public Bitmap getImageFromResource(int id){
		return BitmapFactory.decodeResource(Main_Menu_Activity.getContext().getResources(), id);
	};

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

	public Bitmap getJsonImage(String path) throws IOException {
		InputStream is = Main_Menu_Activity.getContext().getAssets().open(path);
		return BitmapFactory.decodeStream(is);
	}

	public String getType() {
		return type;
	}

	public static int getBuyPriceByGrade(int grade, int _buyPrice){
		return grade * _buyPrice;
	}

	public static int getSellPriceByGrade(int grade, int _sellPrice){
		return grade * _sellPrice;
	}


}
