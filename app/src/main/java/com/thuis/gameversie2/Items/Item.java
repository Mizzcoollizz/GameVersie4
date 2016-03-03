package com.thuis.gameversie2.Items;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Main_Menu_Activity;
import com.thuis.gameversie2.MapScreen.GameView_Activity;
import com.thuis.gameversie2.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.Random;

public abstract class Item {

	private static final int MAX_GRADE_RANDOM = 11;

	protected int sellPrice = 0;

	protected int buyPrice = 0;

	protected int grade = 1;

	public static Bitmap borderImageBitmap = null;

	static{
		setBorderImageBitmap();
	}

	public void setBuyPrice(int buyPrice) {
		this.buyPrice = buyPrice;
	}

	public void setSellPrice(int sellPrice) {
		this.sellPrice = sellPrice;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public Item(){}

	private static boolean setBorderImageBitmap() {
		if(borderImageBitmap == null && Main_Menu_Activity.getContext() != null) {
			Bitmap borderImage = BitmapFactory.decodeResource(Main_Menu_Activity.getContext().getResources(), R.drawable.inventory_item_border);
			borderImageBitmap = Bitmap.createScaledBitmap(borderImage, 72, 72, false);
		return true;
		}
		return false;
	}

	/**
	 * This method is used for getting the items image plus the inventory border
	 * @return the inventory image of the item
	 */
	public abstract Bitmap getInventoryImage();
	public abstract void setInventoryImage();

	public Bitmap createInventoryImage(Bitmap image) {
			setBorderImageBitmap();
			Bitmap newImage = borderImageBitmap.copy(Bitmap.Config.ARGB_4444, true);
			Canvas canvas = new Canvas(newImage);
			Bitmap scaledImage = Bitmap.createScaledBitmap(image, newImage.getWidth(), newImage.getHeight(),false);
			canvas.drawBitmap(scaledImage, 0, 0, null);

		return newImage;
	}

	/**
	 * This method is used for getting the image of the item
	 * @return the items bitmap
	 */
	public abstract Bitmap getImage();

	public Bitmap getImageFromResource(int id){
		return BitmapFactory.decodeResource(Main_Menu_Activity.getContext().getResources(), id);
	};

	public boolean isEqualItem(Item item){
		if(item != null) {
			if (this.grade == item.grade && this.getClass().equals(item.getClass())) {
				return true;
			} else {
				return false;
			}
		}else{
			return false;
		}
	}

	public int getGrade() {
		return grade;
	}

	public abstract String getName();

	public void setRandomGrade() {
		Random random = new Random();
		grade = random.nextInt(MAX_GRADE_RANDOM);
	}

	public static Bitmap getJsonImage(String path){
		InputStream is  = null;
		try {
			is = Main_Menu_Activity.getContext().getAssets().open(path);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			return BitmapFactory.decodeStream(is);
		}
	}


	public abstract String getType();

	public static int getBuyPriceByGrade(int grade, int _buyPrice){
		return grade * _buyPrice;
	}

	public static int getSellPriceByGrade(int grade, int _sellPrice){
		return grade * _sellPrice;
	}

	protected JSONObject getItemJSONObjectFromPath(String path){

		JSONObject json = new JSONObject();
		try {
		InputStream is = GamePanel.getCurrentContext().getApplicationContext().getAssets().open(path);
		BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
		StringBuilder stringBuilder = new StringBuilder();
		int i = 0;
			while ((i = rd.read()) != -1){
                stringBuilder.append((char) i );
            }
		json = new JSONObject(stringBuilder.toString());

		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}finally{
			return json;
		}

	}

	protected void setPricing(Item item){
		item.sellPrice = getSellPriceByGrade(item.grade, item.getStandardSellPrice());
		item.buyPrice = getBuyPriceByGrade(item.grade, item.getStandardBuyPrice());
	}

	public int getSellPrice() {
		return sellPrice;
	}

	public int getBuyPrice() {
		return buyPrice;
	}

	protected abstract void setType(String type);

	protected abstract void setName(String type);

	protected abstract void setJsonGathered(boolean bool);

	protected abstract void loadImage(Bitmap image);

	protected abstract String getPath();

	protected abstract int getStandardSellPrice();

	protected abstract int getStandardBuyPrice();

	protected abstract void setStandardSellPrice(int i);

	protected abstract void setStandardBuyPrice(int i);

	public abstract boolean isJsonGathered();

	public static Item getNewInstance(Item item){
		Item newItem = null;
		try {
			newItem = item.getClass().newInstance();
			newItem.setGrade(item.getGrade());
			newItem.setBuyPrice(item.getBuyPrice());
			newItem.setSellPrice(item.getSellPrice());
		}catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}finally {
			return newItem;
		}
	}


}
