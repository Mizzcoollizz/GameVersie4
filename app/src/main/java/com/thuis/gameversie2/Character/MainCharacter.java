package com.thuis.gameversie2.Character;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.MapScreen.GameView_Activity;
import com.thuis.gameversie2.Inventory_System.Inventory_View.Inventory_Activity;
import com.thuis.gameversie2.Items.Item;
import com.thuis.gameversie2.Items.Rock;
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

	private Item itemHolding = new Rock();

	private Tool toolHolding = null;

	public void setItemHolding(Item itemHolding) {
		this.itemHolding = itemHolding;
	}

	public Item getItemHolding(){
		return itemHolding;
	}

	public static void setMapX(long mapX) {
		MainCharacter.mapX = mapX;
	}

	public static void setMapY(long mapY) {
		MainCharacter.mapY = mapY;
	}

	public  void setDirection(String direction) {
		this.direction = direction;
		animation.setCurrentDirection(direction);

	}

	public static int getMapTileY() {
		return mapTileY;
	}

	public static int getMapTileX() {
		return mapTileX;
	}

	public static void walk(int X, int Y) {
		if(checkCollision()) {
			mapX += X;
			mapY += Y;
		}
	}

	private static boolean checkCollision() {
		if(checkBorderCollision() || MapHandler.getCurrentMap().checkObjectCollision()){
			return false;
		}else{
			return true;
		}
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private static boolean checkBorderCollision(){
		return MapHandler.getCurrentMap().checkBorderCollision(getMapX(), getMapY(), getDirection());
	}

	public Tool getToolHolding() {
		return toolHolding;
	}

	public void setToolHolding(Tool toolHolding) {
		this.toolHolding = toolHolding;
	}

	public static long getMapX() {
		return mapX;
	}

	public static long getMapY() {
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
			if (getHairColor().equals("Blond")) {
				return BitmapFactory.decodeResource(context.getResources(), R.drawable.blond_haar1);
			} else if (getHairColor().equals("Dark Blonde")) {
				return BitmapFactory.decodeResource(context.getResources(), R.drawable.donker_blond_haar1);
			} else {
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
		if(itemHolding != null){
			Bitmap bitmap = itemHolding.getImage();
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

	public static String getDirection() {
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

	public void setItemHolding(Spawnable_Item itemHolding) {
		this.itemHolding = (Item) itemHolding;
	}

	public boolean getOnTouch(MotionEvent event) {
		int X = (int) mapX - (int) MapSurfaceView.getScreenX() - (this.width /2);
		int Y = (int) mapY - (int) MapSurfaceView.getScreenY() - (this.height / 2);

		if(new Rect(X,Y, X + getWidth(), Y + getHeight()).contains((int)event.getX(),(int) event.getY())){
			return true;
		}else{
			return false;
		}


	}

	public void manageOnTouch() {
		if(itemHolding == null || itemHolding.equals(null)){
			Intent intent = new Intent(GameView_Activity.getContext(), Inventory_Activity.class);
			GameView_Activity.getContext().startActivity(intent);
		}else if(!itemHolding.equals(null)){
			if(GamePanel.getInventory().add(getItemHolding())){
				this.itemHolding = null;
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
}
