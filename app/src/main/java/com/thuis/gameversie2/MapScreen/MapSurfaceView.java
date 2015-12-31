package com.thuis.gameversie2.MapScreen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.thuis.gameversie2.Character.MainCharacter;
import com.thuis.gameversie2.GamePanel;
import com.thuis.gameversie2.Interactive.Field;
import com.thuis.gameversie2.Interactive.Interactive;
import com.thuis.gameversie2.Items.Item;
import com.thuis.gameversie2.Items.Spawnable_Item;
import com.thuis.gameversie2.Map.ItemSpawnArea;
import com.thuis.gameversie2.Map.Layer;
import com.thuis.gameversie2.Map.Maps.Map;
import com.thuis.gameversie2.Map.MapHandler;

import java.util.ArrayList;

/**
 * Created by Elize on 31-7-2015.
 */
public class MapSurfaceView extends SurfaceView implements SurfaceHolder.Callback{

    private SurfaceHolder surfaceHolder = null;
    private static int screen_width_tiles = 0;
    private static int screen_height_tiles = 0;
    private static long screen_width = 0;
    private static long screen_height = 0;
    private GameThread gameThread = null;
    private static int topScreenTileX = 0;
    private static int topScreenTileY = 0;
    private static long screenX = 0;
    private static long screenY = 0;
    private MainCharacter player = null;

    public static long getScreen_width() {
        return screen_width;
    }

    public static long getScreen_height() {
        return screen_height;
    }


    public static long getScreenX(){
        return screenX;
    }

    public static long getScreenY(){
        return screenY;

    }

    public static Rect getScreenRect(){
        Map map = MapHandler.getCurrentMap();
        //if(screenX != 0 && screenY != 0){
            return new Rect((int)screenX - map.getTile_width(),
                    (int)screenY - map.getTile_height(),
                    (int)screenX + (int)screen_width + map.getTile_width(),
                    (int)screenY + (int)screen_height + map.getTile_height());
//        }else{
//            return null;
//        }
    }


    public MapSurfaceView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        gameThread = new GameThread(getHolder(), this);

    }

    public MapSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        gameThread = new GameThread(getHolder(), this);

    }

    public MapSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
        gameThread = new GameThread(getHolder(), this);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screen_width_tiles = this.getWidth() / 32;
        screen_height_tiles = this.getHeight() /32;
        screen_height = this.getHeight();
        screen_width = this.getWidth();
        setOnTouchListener(new OnInteractionTouch());
        if(gameThread == null){
            gameThread = new GameThread(getHolder(), this);
        }
        player = GamePanel.getPlayer();
        gameThread.setRunning(true);
        gameThread.startThread();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean finished = false;
        while(!finished){
            try{
                gameThread.setRunning(false);
                gameThread.join();
                gameThread = null;
                finished = true;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void drawScreen(Canvas canvas) throws Exception {

        if (canvas != null) {
            Layer backgroundLayer = null;
            Layer plantsLayer = null;
            ArrayList<Layer> layers = MapHandler.getCurrentMap().getLayers();
            for (int layerNumber = 0; layerNumber < layers.size(); layerNumber++) {
                if (layers.get(layerNumber).getName().equals(MapHandler.getCurrentMap().BACKGROUND_LAYER_NAME)) {
                    backgroundLayer = layers.get(layerNumber);
                }else if(layers.get(layerNumber).getName().equals(MapHandler.getCurrentMap().PLANTS_LAYER_NAME)){
                    plantsLayer = layers.get(layerNumber);
                }
            }

            drawLayer(backgroundLayer, canvas);
            drawLayer(plantsLayer,canvas);
            drawInteractiveLayer(canvas);
            drawItemsBehindPlayer(canvas);
            player.draw(canvas);
            drawItemsInFrondOfPlayer(canvas);
        }
    }

    private void drawItemsInFrondOfPlayer(Canvas canvas) {
        ArrayList<Interactive> itemsInScreen = MapHandler.getCurrentMap().getItemSpawnAreasInScreen();
        for(Interactive interactive: itemsInScreen) {
            if (interactive.getYLocation() > player.getMapY()) {
                drawItem(interactive, canvas);
            }
        }
    }

    private void drawItem(Interactive interactive, Canvas canvas){
        if (interactive.getClass().isInstance(ItemSpawnArea.class)) {
            ItemSpawnArea itemSpawnArea = (ItemSpawnArea) interactive;
            if (itemSpawnArea.hasItem()) {
                Item item = (Item) itemSpawnArea.getItem();
                canvas.drawBitmap(item.getImage(),
                        itemSpawnArea.getXLocation() - getScreenX(),
                        itemSpawnArea.getYLocation() - getScreenY(),
                        null);
            }else if(itemSpawnArea.hasInteractive()){
                Interactive interactiveInArea = itemSpawnArea.getInteractive();
                canvas.drawBitmap(interactiveInArea.getImage(),
                        itemSpawnArea.getXLocation() - getScreenX(),
                        itemSpawnArea.getYLocation() - getScreenY(),
                        null);
            }
        }else if (interactive.getClass().isInstance(Field.class)) {
            Field field = (Field) interactive;
            if (field.hasCrop()) {
                Item item = (Item) field.getItem();
                canvas.drawBitmap(item.getImage(),
                        field.getXLocation() - getScreenX(),
                        field.getYLocation() - getScreenY(),
                        null);
            }
        }
    }

    private void drawItemsBehindPlayer(Canvas canvas) {
        ArrayList<Interactive> itemsInScreen = MapHandler.getCurrentMap().getItemSpawnAreasInScreen();
        for(Interactive interactive: itemsInScreen) {
            if (interactive.getYLocation() <= player.getMapY()) {
                    drawItem(interactive, canvas);
                }
            }
        }


    private void drawInteractiveLayer(Canvas canvas) {
        ArrayList<Interactive> interactiveArrayList = MapHandler.getCurrentMap().getAllInteractiveOnScreen();
        for(Interactive object: interactiveArrayList){
            try {
                Bitmap image = object.getImage();
                long x = object.getXLocation() - getScreenX();
                long y = object.getYLocation() - getScreenY();
                canvas.drawBitmap(image, x, y, null);
            }catch(NullPointerException ex){
                Log.i("tag", "Nullpointerexception: interactive:" + object.getClass().toString());
            }
        }
    }

    private Bitmap getImage(int tileX, int tileY, Layer layer) {

        try {
            int id = layer.getCoordinates()[tileY][tileX];
            if (id > 0) {
                return MapHandler.getCurrentMap().getImage(id);
            } else {
                return null;
            }
        }catch(IndexOutOfBoundsException ex){
            Log.d(getClass().toString(), "index out of boundsException X:" + tileX + "Y:" + tileY);

            return null;
        }
    }

    private void drawLayer(Layer layer, Canvas canvas){
        if(layer != null) {
            screenX = player.getMapX() - (long) (screen_width / 2.0);
            screenY = player.getMapY() - (long) (screen_height / 2.0);

            int startTileX = calculateStartTileX();
            int startTileY = calculateStartTileY();

            topScreenTileX = startTileX;
            topScreenTileY = startTileY;

            int startX = -(int) screenX % MapHandler.getCurrentMap().getTile_width();
            int startY = -(int) screenY % MapHandler.getCurrentMap().getTile_height();

            int mapToDrawY = startTileY;

            for (int tileY = 0; tileY <= screen_height_tiles + 2; tileY++) {
                int mapToDrawX = startTileX;
                for (int tileX = 0; tileX <= screen_width_tiles + 2; tileX++) {

                    Bitmap bitmap = getImage(mapToDrawX, mapToDrawY, layer);
                    if (bitmap != null) {
                        canvas.drawBitmap(bitmap,
                                startX + tileX * MapHandler.getCurrentMap().getTile_width(),
                                startY + tileY * MapHandler.getCurrentMap().getTile_height(),
                                null);
                    }
                    mapToDrawX++;
                }
                mapToDrawY++;
            }
        }
    }

    private int calculateStartTileY() {
        if(screenY < 0){
            screenY = 0;
            return 0;
        }else if(screenY + screen_height > (MapHandler.getCurrentMap().getMap_height() * MapHandler.getCurrentMap().getTile_height())){
            screenY = (MapHandler.getCurrentMap().getMap_height() * MapHandler.getCurrentMap().getTile_height()) - screen_height;
            return (int) Math.floor(screenY / (double) MapHandler.getCurrentMap().getTile_height());
        }else {
            return (int) Math.floor(screenY / (double) MapHandler.getCurrentMap().getTile_height());
        }
    }

    //calculate the top X tile of the screen.
    //the screen will move with the player, if the end of the map is not reached.
    private int calculateStartTileX() {
        if(screenX <= 0){
            screenX = 0;
            return 0;
        }else if(screenX + screen_width > (MapHandler.getCurrentMap().getMap_width() * MapHandler.getCurrentMap().getTile_width())){
            screenX = (MapHandler.getCurrentMap().getMap_width() * MapHandler.getCurrentMap().getTile_width()) - screen_width;
            return (int) Math.floor(screenX/ (double) MapHandler.getCurrentMap().getTile_width());
        }else{
            return  (int) Math.floor(screenX / (double) MapHandler.getCurrentMap().getTile_width());
        }
    }


    public void update() {
        GamePanel.getPlayer().update();
    }
}
