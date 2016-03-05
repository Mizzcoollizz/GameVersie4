package com.thuis.gameversie2.Character;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Inventory_System.Inventory_Slot;
import com.thuis.gameversie2.MapScreen.GameView_Activity;
import com.thuis.gameversie2.Inventory_System.Inventory_View.Inventory_Activity;
import com.thuis.gameversie2.Items.Item;
import com.thuis.gameversie2.Items.Raw_Materials.Rock;
import com.thuis.gameversie2.Items.Spawnable_Item;
import com.thuis.gameversie2.Items.Tools.Tool;
import com.thuis.gameversie2.Map.MapHandler;
import com.thuis.gameversie2.MapScreen.MapSurfaceView;
import com.thuis.gameversie2.MapScreen.MovePlayerButtonListener;
import com.thuis.gameversie2.R;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MainCharacter {

	Bitmap inventoryImage = null;

	private String hairColor = "Blond";

	private Context context = null;
	private Bitmap toolHoldingInventoryImage;

	public int getSpeed() {
		return speed;
	}

	private int speed = 4;

	private String name;

	private String gender;

	private String race;

	private Character spouse;

	private Character child;

	private ArrayList knownCharacters;

	public static int gidLocationPlayer = 318;

	private static int mapTileX = 35;

	private static int mapTileY = 100;

	private static long mapX = 32 * mapTileX;

	private static long mapY = 32 * mapTileY;

	private CharacterAnimation animation = new CharacterAnimation();

	private int width = 32;

	private int height = 64;

	private static String direction =  "down";

	public boolean walking = false;

	private Inventory_Slot itemHolding = null;

	private Tool toolHolding = null;

	public void setItemHolding(Inventory_Slot itemHolding) {
		this.itemHolding = itemHolding;
	}

	public Inventory_Slot getItemHolding(){
		return itemHolding;
	}

	public void setMapX(long mapX) {
		MainCharacter.mapX = mapX;
	}

	public void setMapY(long mapY) {
		MainCharacter.mapY = mapY;
	}

	public  void setDirection(String direction) {
		MainCharacter.direction = direction;
		animation.setCurrentDirection(direction);
	}

	public int getMapTileY() {
		return mapTileY;
	}

	public int getMapTileX() {
		return mapTileX;
	}

	public void walk(int X, int Y) {
		if(checkCollision()) {
			mapX += X;
			mapY += Y;
		}
	}

	private boolean checkCollision() {
//		if(checkBorderCollision() || MapHandler.getCurrentMap().checkObjectCollision()){
//			return false;
//		}else{
//			return true;
//		}
		return !checkBorderCollision() && !MapHandler.getCurrentMap().checkObjectCollision();
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private boolean checkBorderCollision(){
		return MapHandler.getCurrentMap().checkBorderCollision(getMapX(), getMapY(), getDirection());
	}

	public Tool getToolHolding() {
		return toolHolding;
	}

	public void setToolHolding(Tool toolHolding) {
		this.toolHolding = toolHolding;
	}

	public long getMapX() {
		return mapX;
	}

	public long getMapY() {
		return mapY;
	}

	public MainCharacter(String gender, String race, String name, Context context) {
		this.context = context;
		this.gender = gender;
		this.race = race;
		this.name = name;
		Bitmap[][] image = new Bitmap[4][4];

		Bitmap resource = getImages(gender, race);

		int imgHeight = resource.getHeight() / 4;
		int imgWidth = resource.getWidth() / 4;

		for(int i = 0; i < image.length; i++){
			for(int i2 = 0; i2 < image[i].length; i2++){
				image[i][i2] = Bitmap.createBitmap(resource, i2 * imgWidth, i * imgHeight, imgWidth, imgHeight);
			}
		}

		setInventoryImage();
		animation.setFrames(image);
		animation.setDelay(250);

	}

	private Bitmap getImages(String gender, String race) {
		if(gender.equals("male") && race.equals("human")){
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.outHeight = height * 4;
			options.outWidth = width * 4;
			Bitmap image = BitmapFactory.decodeResource(context.getResources(), R.drawable.mens_character_man, options);
			image = image.copy(Bitmap.Config.ARGB_8888, true);

			Canvas canvas = new Canvas(image);
			canvas.drawBitmap(getHairImages(), 0, 0, null);
			return image;
		}else if(gender.equals("male")){
			return null;

		}else{
			return null;
		}
	}

	private Bitmap getHairImages() {
		try {
			switch (getHairColor()) {
				case "Blond":
					return BitmapFactory.decodeResource(context.getResources(), R.drawable.blond_haar1);
				case "Dark Blonde":
					return BitmapFactory.decodeResource(context.getResources(), R.drawable.donker_blond_haar1);
				default:
					throw new FileNotFoundException();
			}
		}catch (FileNotFoundException ex){
			//File not found!
			return null;
		}
	}

	public void setSpouse() {

	}

	public void addCharacterToList() {

	}

	public void update(){
		MovePlayerButtonListener.checkButtonMovement();
		calculateTileXandTileY();
	}

	private void calculateTileXandTileY() {
		mapTileX = (int) Math.floor(mapX / MapHandler.getCurrentMap().getTile_width());
		mapTileY = (int) Math.floor(mapY / MapHandler.getCurrentMap().getTile_height());
	}

	public void draw(Canvas canvas){
		if(direction.equals("up")){
			drawItemHolding(canvas);
			drawPlayer(canvas);
		}else{
			drawPlayer(canvas);
			drawItemHolding(canvas);
		}
	}

	private void drawPlayer(Canvas canvas){
		long X = mapX - MapSurfaceView.getScreenX() -(this.width /2);
		long Y = mapY - MapSurfaceView.getScreenY() - (this.height / 2);
		Bitmap image = animation.getImage();
		canvas.drawBitmap(image, X, Y, null);
	}

	private void drawItemHolding(Canvas canvas){
		if(itemHolding != null && !itemHolding.isEmpty()){
			Bitmap bitmap = itemHolding.getItem().getImage();
			canvas.drawBitmap(
					bitmap,
					mapX - MapSurfaceView.getScreenX() - bitmap.getWidth() / 2,
					mapY - MapSurfaceView.getScreenY() - bitmap.getHeight() / 2,
					null);
		}
	}

	public void setWalking(boolean _walking){
		this.walking = _walking;
	}

	public boolean isWalking() {
		return walking;
	}

	public String getDirection() {
		return direction;
	}

	public Rect getRect(){
		int left = (int) (mapX - (this.width / 2));
		int top = (int) mapY;
		int right = left + this.getWidth();
		int bottom = top + (this.getHeight() / 2);
		return new Rect(left, top, right, bottom);
	}

	public String getHairColor() {
		return hairColor;
	}

	public boolean getOnTouch(MotionEvent event) {
		int X = (int) mapX - (int) MapSurfaceView.getScreenX() - (this.width /2);
		int Y = (int) mapY - (int) MapSurfaceView.getScreenY() - (this.height / 2);

		return new Rect(X, Y, X + getWidth(), Y + getHeight()).contains((int) event.getX(), (int) event.getY());


	}

	/**
	 * If the player is not holding anything, go to inventory
	 * Otherwise, store item
	 */
	public void manageOnTouch() {
		if(itemHolding == null || itemHolding.isEmpty()){
//			Intent intent = new Intent(GamePanel.getCurrentContext(), Inventory_Activity.class);
//			GamePanel.getCurrentContext().startActivity(intent);
		}else {
			if(GamePanel.getInventory().addItemSlot(getItemHolding())){
				this.itemHolding = new Inventory_Slot();
			}else{
				//TODO error message!
			}
		}

	}

	public Bitmap getImage() {
		return animation.getImage();
	}

	public void setInventoryImage() {
		Bitmap resource = getImages(gender, race);
		int imgWidth = (resource.getWidth() / 4);
		int imgHeight = (resource.getHeight() / 4);
		Bitmap image = Bitmap.createBitmap(resource, 0, 0, imgWidth, imgHeight);
		image = image.copy(Bitmap.Config.ARGB_4444, true);
		Canvas canvas = new Canvas(image);
		Bitmap hair = Bitmap.createBitmap(getHairImages(), 0, 0, imgWidth, imgHeight);
		canvas.drawBitmap(hair, 0, 0, null);
		inventoryImage = image;
	}

	public Bitmap getInventoryImage() {
		return inventoryImage;
	}

	public boolean isHoldingTool() {
		return (getToolHolding() != null);
	}

	public Bitmap getItemHoldingInventoryImage() {
		if(itemHolding != null && itemHolding.getItem() != null){
			return itemHolding.getItem().getInventoryImage();
		}else{
			return Item.borderImageBitmap;
		}
	}

	public Bitmap getToolHoldingInventoryImage() {
		if(toolHolding != null){
			return toolHolding.getInventoryImage();
		}else{
			return Item.borderImageBitmap;
		}

	}

	public Rect getInteractionRect(){
		int rectWidth = MapHandler.getCurrentMap().getTile_width() * 3;
		int rectHeight = MapHandler.getCurrentMap().getTile_height() * 3;

		int left = 0;
		int top = 0;
		int right = 0;
		int bottom = 0;

		switch(direction){
			case "up":
				left = (int) getMapX() - (rectWidth / 2);
				top = (int) getMapY() - (rectHeight);
				break;
			case "down":
				left = (int) getMapX() - (rectWidth / 2);
				top = (int) getMapY();
				break;
			case "left":
				left = (int) getMapX() - rectWidth;
				top = (int) getMapY() - (rectHeight / 2);
				break;
			case "right":
				left = (int) getMapX();
				top = (int) getMapY() - (rectHeight / 2);

				break;
		}

		right = left + rectWidth;
		bottom = top + rectHeight;

		return new Rect(left, top, right, bottom);
	}
}
